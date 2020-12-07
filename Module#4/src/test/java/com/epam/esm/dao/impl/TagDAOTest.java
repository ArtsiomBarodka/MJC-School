package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.model.entity.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Sql({"classpath:dump/insert.sql"})
@DataJpaTest
class TagDAOTest {
    private static final String TAG_NAME = "name";

    @Autowired
    private TagDAO tagDAO;

    private final Tag tag;

    public TagDAOTest() {
        tag = new Tag();
        tag.setName(TAG_NAME);
    }

    @AfterEach
    void deleteTagInDb() {
        if (tagDAO.existsById(tag.getId())) {
            tagDAO.deleteById(tag.getId());
        }
    }

    @BeforeEach
    void createTagInDb() {
        tagDAO.save(tag);
    }

    @Test
    void findTheMostWidelyUsedOfUsersWithTheHighestCostOfAllOrdersTest_SHOULD_RETURN_TAG() {
        assertThat(tagDAO.findTheMostWidelyUsedOfUsersWithTheHighestCostOfAllOrders()).isNotEmpty();
    }

    @Test
    void existsByNameTest_SHOULD_RETURN_TRUE() {
        assertThat(tagDAO.existsByName(tag.getName())).isTrue();
    }

    @Test
    void existsByNameTest_SHOULD_RETURN_FALSE() {
        assertThat(tagDAO.existsByName(" ")).isFalse();
    }

    @Test
    void getByGiftCertificates_IdTest_SHOULD_RETURN_PAGE_OF_TAGS(){
        Long existingGiftCertificateId = 1L;

        assertThat(tagDAO.getByGiftCertificates_Id(existingGiftCertificateId, Pageable.unpaged())).isNotEmpty();
    }

    @Test
    void getByGiftCertificates_IdTest_SHOULD_RETURN_EMPTY_RESULT(){
        Long notExistingGiftCertificateId = 10L;

        assertThat(tagDAO.getByGiftCertificates_Id(notExistingGiftCertificateId, Pageable.unpaged())).isEmpty();
    }

    @Test
    void getByNameInTest_SHOULD_RETURN_ITERABLE_OF_TAGS(){
        List<String> existingNames = new ArrayList<>();
        existingNames.add(tag.getName());

        assertThat(tagDAO.getByNameIn(existingNames)).contains(tag);
    }

    @Test
    void getByNameInTest_SHOULD_RETURN_RETURN_EMPTY_RESULT(){
        List<String> notExistingNames = new ArrayList<>();
        notExistingNames.add(" ");

        assertThat(tagDAO.getByNameIn(notExistingNames)).isEmpty();
    }
}
