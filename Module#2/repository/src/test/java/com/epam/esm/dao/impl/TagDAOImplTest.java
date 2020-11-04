package com.epam.esm.dao.impl;

import com.epam.esm.configuration.DataConfiguration;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.repository.RepositoryException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DataConfiguration.class)
public class TagDAOImplTest {
    private static final String TAG_NAME = "name";

    private TagDAO tagDAO;
    private Tag tag;

    @Autowired
    public TagDAOImplTest(DataSource testDataSource) {
        tagDAO = new TagDAOImpl(testDataSource);
        tag = new Tag();
        tag.setName(TAG_NAME);
    }

    @AfterEach
    void deleteTagInDb() throws RepositoryException {
        tagDAO.delete(tag.getId());
    }

    @BeforeEach
    void createTagInDb() throws RepositoryException {
        tag.setId(tagDAO.create(tag));
    }


    @Test
    void isAlreadyExistByNameTest_SHOULD_RETURN_TRUE() throws RepositoryException {
        assertThat(tagDAO.isAlreadyExistByName(tag.getName())).isTrue();
    }

    @Test
    void isAlreadyExistByNameTest_SHOULD_RETURN_FALSE() throws RepositoryException {
        assertThat(tagDAO.isAlreadyExistByName(" ")).isFalse();
    }

    @Test
    void createTest_SHOULD_CREATE_TAG_AND_RETURN_VALID_ID() throws RepositoryException {
        Tag newTag = new Tag();
        newTag.setName("tag");

        assertThat(tagDAO.isAlreadyExistByName(newTag.getName())).isFalse();

        Long newId = tagDAO.create(newTag);

        assertThat(newId).isGreaterThan(0L);
        assertThat(tagDAO.isAlreadyExistByName(newTag.getName())).isTrue();
    }

    @Test
    void findByIdTest_SHOULD_RETURN_VALID_TAG() throws RepositoryException {
        assertThat(tagDAO.findById(tag.getId())).contains(tag);
    }

    @Test
    void findByIdTest_SHOULD_RETURN_EMPTY_RESULT() throws RepositoryException {
        Long notExistingTagId = 10L;

        assertThat(tagDAO.findById(notExistingTagId)).isEmpty();
    }

    @Test
    void getListTagsByGiftCertificateIdTest_SHOULD_RETURN_NOT_EMPTY_TAGS_LIST() throws RepositoryException {
        Long giftCertificateId = 2L;

        assertThat(tagDAO.getListTagsByGiftCertificateId(giftCertificateId)).isNotEmpty();
    }

    @Test
    void getListTagsByGiftCertificateIdTest_SHOULD_RETURN_EMPTY_RESULT() throws RepositoryException {
        Long notExistingGiftCertificateId = 20L;

        assertThat(tagDAO.getListTagsByGiftCertificateId(notExistingGiftCertificateId)).isEmpty();
    }

    @Test
    void deleteTest_SHOULD_DELETE_TAG() throws RepositoryException {
        assertThat(tagDAO.isAlreadyExistByName(tag.getName())).isTrue();

        tagDAO.delete(tag.getId());

        assertThat(tagDAO.isAlreadyExistByName(tag.getName())).isFalse();
    }
}
