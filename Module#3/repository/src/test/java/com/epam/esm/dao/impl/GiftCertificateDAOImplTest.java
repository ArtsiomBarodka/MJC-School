package com.epam.esm.dao.impl;

import com.epam.esm.TestMain;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.domain.Page;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@Sql({"classpath:dump/insert.sql"})
@SpringBootTest(classes = TestMain.class)
class GiftCertificateDAOImplTest {
    private static final String GIFT_CERTIFICATE_NAME = "name";
    private static final String GIFT_CERTIFICATE_DESCRIPTION = "Some description for gift certificate!";
    private static final Double GIFT_CERTIFICATE_PRICE = 100.25;
    private static final Integer GIFT_CERTIFICATE_DURATION = 7;

    @Autowired
    private GiftCertificateDAO giftCertificateDAO;

    private final GiftCertificate giftCertificate;

    public GiftCertificateDAOImplTest() {
        giftCertificate = new GiftCertificate();
        giftCertificate.setName(GIFT_CERTIFICATE_NAME);
        giftCertificate.setDescription(GIFT_CERTIFICATE_DESCRIPTION);
        giftCertificate.setPrice(GIFT_CERTIFICATE_PRICE);
        giftCertificate.setDuration(GIFT_CERTIFICATE_DURATION);
        giftCertificate.setTags(Collections.emptyList());
    }

    @AfterEach
    void deleteGiftCertificateInDb() {
        if (giftCertificateDAO.isExistById(giftCertificate.getId())) {
            giftCertificateDAO.delete(giftCertificate.getId());
        }
    }

    @BeforeEach
    void createGiftCertificateInDb() {
        giftCertificateDAO.save(giftCertificate);
    }

    @Test
    void isExistByIdTest_SHOULD_RETURN_TRUE() {
        assertThat(giftCertificateDAO.isExistById(giftCertificate.getId())).isTrue();
    }

    @Test
    void isExistByIdTest_SHOULD_RETURN_FALSE() {
        assertThat(giftCertificateDAO.isExistById(100L)).isFalse();
    }

    @Test
    void isExistByNameTest_SHOULD_RETURN_TRUE() {
        assertThat(giftCertificateDAO.isExistByName(giftCertificate.getName())).isTrue();
    }

    @Test
    void isExistByNameTest_SHOULD_RETURN_FALSE() {
        assertThat(giftCertificateDAO.isExistByName(" ")).isFalse();
    }

    @Test
    void saveTest_SHOULD_CREATE_NEW_GIFT_CERTIFICATE_AND_RETURN_WITH_VALID_ID() {
        GiftCertificate newGiftCertificate = new GiftCertificate();
        newGiftCertificate.setName("giftCertificate");
        newGiftCertificate.setDescription("some desc");
        newGiftCertificate.setPrice(22.2);
        newGiftCertificate.setDuration(8);
        newGiftCertificate.setTags(Collections.emptyList());

        assertThat(giftCertificateDAO.isExistByName(newGiftCertificate.getName())).isFalse();

        GiftCertificate repositoryGiftCertificate = giftCertificateDAO.save(newGiftCertificate);

        assertThat(repositoryGiftCertificate).isNotNull();
        assertThat(repositoryGiftCertificate.getId()).isNotNull().isGreaterThan(0L);
        assertThat(giftCertificateDAO.isExistByName(newGiftCertificate.getName())).isTrue();
    }

    @Test
    void saveTest_SHOULD_UPDATE_NOT_MANAGED_CURRENT_GIFT_CERTIFICATE_AND_RETURN() {
        String newName = "newName";

        assertThat(giftCertificateDAO.isExistByName(newName)).isFalse();

        giftCertificate.setName(newName);
        GiftCertificate updatedGiftCertificate = giftCertificateDAO.save(giftCertificate);

        assertThat(updatedGiftCertificate).isNotNull();
        assertThat(giftCertificateDAO.isExistByName(newName)).isTrue();
    }

    @Test
    void findByIdTest_SHOULD_RETURN_VALID_GIFT_CERTIFICATE() {
        Optional<GiftCertificate> repositoryGiftCertificate = giftCertificateDAO.findById(giftCertificate.getId());

        assertThat(repositoryGiftCertificate).isPresent();
        assertThat(repositoryGiftCertificate.get()).hasFieldOrProperty(giftCertificate.getName());
    }

    @Test
    void findByIdTest_SHOULD_RETURN_EMPTY_RESULT() {
        Long notExistingTagId = 10L;

        assertThat(giftCertificateDAO.findById(notExistingTagId)).isEmpty();
    }

    @Test
    void deleteTest_SHOULD_DELETE_GIFT_CERTIFICATE() {
        assertThat(giftCertificateDAO.isExistById(giftCertificate.getId())).isTrue();

        giftCertificateDAO.delete(giftCertificate.getId());

        assertThat(giftCertificateDAO.isExistById(giftCertificate.getId())).isFalse();
    }

    @ParameterizedTest
    @EnumSource(value = SortMode.class)
    void listAllTest_SHOULD_RETURN_NOT_EMPTY_GIFT_CERTIFICATES_LIST(SortMode sortMode) {
        Page page = new Page();
        assertThat(giftCertificateDAO.listAll(page, sortMode)).isNotEmpty();
    }

    @ParameterizedTest
    @EnumSource(value = SortMode.class)
    void listByTagNamesTest_SHOULD_RETURN_EMPTY_GIFT_CERTIFICATES_LIST(SortMode sortMode) {
        Page page = new Page();
        List<String> notExistingTagNames = new ArrayList<>();
        notExistingTagNames.add("newTag");

        assertThat(giftCertificateDAO.listByTagNames(notExistingTagNames, page, sortMode)).isEmpty();
    }

    @ParameterizedTest
    @EnumSource(value = SortMode.class)
    void listByTagNamesTest_SHOULD_RETURN_NOT_EMPTY_GIFT_CERTIFICATES_LIST(SortMode sortMode) {
        Page page = new Page();
        List<String> existingTagNames = new ArrayList<>();
        existingTagNames.add("one");

        assertThat(giftCertificateDAO.listByTagNames(existingTagNames, page, sortMode)).isNotEmpty();
    }

    @Test
    void countAllTest_SHOULD_RETURN_POSITIVE_COUNT() {
        assertThat(giftCertificateDAO.countAll()).isPositive();
    }

    @Test
    void countByTagNamesTest_SHOULD_RETURN_ZERO_COUNT() {
        List<String> notExistingTagNames = new ArrayList<>();
        notExistingTagNames.add("newTag");

        assertThat(giftCertificateDAO.countByTagNames(notExistingTagNames)).isZero();
    }

    @Test
    void countByTagNamesTest_SHOULD_RETURN_POSITIVE_COUNT() {
        List<String> existingTagNames = new ArrayList<>();
        existingTagNames.add("one");

        assertThat(giftCertificateDAO.countByTagNames(existingTagNames)).isPositive();
    }
}
