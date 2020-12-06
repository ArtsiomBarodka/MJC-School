package com.epam.esm.dao.impl;

import com.epam.esm.Main;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.model.domain.Page;
import com.epam.esm.model.domain.SortMode;
import com.epam.esm.model.entity.GiftCertificate;
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

/**
 * The type Gift certificate dao com.epam.esm.dao.impl test.
 */
@ActiveProfiles("test")
@Transactional
@Sql({"classpath:dump/insert.sql"})
@SpringBootTest(classes = Main.class)
class GiftCertificateDAOImplTest {
    private static final String GIFT_CERTIFICATE_NAME = "name";
    private static final String GIFT_CERTIFICATE_DESCRIPTION = "Some description for gift certificate!";
    private static final Double GIFT_CERTIFICATE_PRICE = 100.25;
    private static final Integer GIFT_CERTIFICATE_DURATION = 7;

    @Autowired
    private GiftCertificateDAO giftCertificateDAO;

    private final GiftCertificate giftCertificate;

    /**
     * Instantiates a new Gift certificate dao com.epam.esm.dao.impl test.
     */
    public GiftCertificateDAOImplTest() {
        giftCertificate = new GiftCertificate();
        giftCertificate.setName(GIFT_CERTIFICATE_NAME);
        giftCertificate.setDescription(GIFT_CERTIFICATE_DESCRIPTION);
        giftCertificate.setPrice(GIFT_CERTIFICATE_PRICE);
        giftCertificate.setDuration(GIFT_CERTIFICATE_DURATION);
        giftCertificate.setTags(Collections.emptyList());
    }

    /**
     * Delete gift certificate in db.
     */
    @AfterEach
    void deleteGiftCertificateInDb() {
        if (giftCertificateDAO.isExistById(giftCertificate.getId())) {
            giftCertificateDAO.delete(giftCertificate.getId());
        }
    }

    /**
     * Create gift certificate in db.
     */
    @BeforeEach
    void createGiftCertificateInDb() {
        giftCertificateDAO.save(giftCertificate);
    }

    /**
     * Is exist by id test should return true.
     */
    @Test
    void isExistByIdTest_SHOULD_RETURN_TRUE() {
        assertThat(giftCertificateDAO.isExistById(giftCertificate.getId())).isTrue();
    }

    /**
     * Is exist by id test should return false.
     */
    @Test
    void isExistByIdTest_SHOULD_RETURN_FALSE() {
        assertThat(giftCertificateDAO.isExistById(100L)).isFalse();
    }

    /**
     * Is exist by name test should return true.
     */
    @Test
    void isExistByNameTest_SHOULD_RETURN_TRUE() {
        assertThat(giftCertificateDAO.isExistByName(giftCertificate.getName())).isTrue();
    }

    /**
     * Is exist by name test should return false.
     */
    @Test
    void isExistByNameTest_SHOULD_RETURN_FALSE() {
        assertThat(giftCertificateDAO.isExistByName(" ")).isFalse();
    }

    /**
     * Save test should create new gift certificate and return with valid id.
     */
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

    /**
     * Save test should update not managed current gift certificate and return.
     */
    @Test
    void saveTest_SHOULD_UPDATE_NOT_MANAGED_CURRENT_GIFT_CERTIFICATE_AND_RETURN() {
        String newName = "newName";

        assertThat(giftCertificateDAO.isExistByName(newName)).isFalse();

        giftCertificate.setName(newName);
        GiftCertificate updatedGiftCertificate = giftCertificateDAO.save(giftCertificate);

        assertThat(updatedGiftCertificate).isNotNull();
        assertThat(giftCertificateDAO.isExistByName(newName)).isTrue();
    }

    /**
     * Find by id test should return valid gift certificate.
     */
    @Test
    void findByIdTest_SHOULD_RETURN_VALID_GIFT_CERTIFICATE() {
        Optional<GiftCertificate> repositoryGiftCertificate = giftCertificateDAO.findById(giftCertificate.getId());

        assertThat(repositoryGiftCertificate).isPresent();
        assertThat(repositoryGiftCertificate.get()).hasFieldOrProperty(giftCertificate.getName());
    }

    /**
     * Find by id test should return empty result.
     */
    @Test
    void findByIdTest_SHOULD_RETURN_EMPTY_RESULT() {
        Long notExistingTagId = 10L;

        assertThat(giftCertificateDAO.findById(notExistingTagId)).isEmpty();
    }

    /**
     * Delete test should delete gift certificate.
     */
    @Test
    void deleteTest_SHOULD_DELETE_GIFT_CERTIFICATE() {
        assertThat(giftCertificateDAO.isExistById(giftCertificate.getId())).isTrue();

        giftCertificateDAO.delete(giftCertificate.getId());

        assertThat(giftCertificateDAO.isExistById(giftCertificate.getId())).isFalse();
    }

    /**
     * List all test should return not empty gift certificates list.
     *
     * @param sortMode the sort mode
     */
    @ParameterizedTest
    @EnumSource(value = SortMode.class)
    void listAllTest_SHOULD_RETURN_NOT_EMPTY_GIFT_CERTIFICATES_LIST(SortMode sortMode) {
        Page page = new Page();
        assertThat(giftCertificateDAO.listAll(page, sortMode)).isNotEmpty();
    }

    /**
     * List by tag names test should return empty gift certificates list.
     *
     * @param sortMode the sort mode
     */
    @ParameterizedTest
    @EnumSource(value = SortMode.class)
    void listByTagNamesTest_SHOULD_RETURN_EMPTY_GIFT_CERTIFICATES_LIST(SortMode sortMode) {
        Page page = new Page();
        List<String> notExistingTagNames = new ArrayList<>();
        notExistingTagNames.add("newTag");

        assertThat(giftCertificateDAO.listByTagNames(notExistingTagNames, page, sortMode)).isEmpty();
    }

    /**
     * List by tag names test should return not empty gift certificates list.
     *
     * @param sortMode the sort mode
     */
    @ParameterizedTest
    @EnumSource(value = SortMode.class)
    void listByTagNamesTest_SHOULD_RETURN_NOT_EMPTY_GIFT_CERTIFICATES_LIST(SortMode sortMode) {
        Page page = new Page();
        List<String> existingTagNames = new ArrayList<>();
        existingTagNames.add("one");

        assertThat(giftCertificateDAO.listByTagNames(existingTagNames, page, sortMode)).isNotEmpty();
    }

    /**
     * Count all test should return positive count.
     */
    @Test
    void countAllTest_SHOULD_RETURN_POSITIVE_COUNT() {
        assertThat(giftCertificateDAO.countAll()).isPositive();
    }

    /**
     * Count by tag names test should return zero count.
     */
    @Test
    void countByTagNamesTest_SHOULD_RETURN_ZERO_COUNT() {
        List<String> notExistingTagNames = new ArrayList<>();
        notExistingTagNames.add("newTag");

        assertThat(giftCertificateDAO.countByTagNames(notExistingTagNames)).isZero();
    }

    /**
     * Count by tag names test should return positive count.
     */
    @Test
    void countByTagNamesTest_SHOULD_RETURN_POSITIVE_COUNT() {
        List<String> existingTagNames = new ArrayList<>();
        existingTagNames.add("one");

        assertThat(giftCertificateDAO.countByTagNames(existingTagNames)).isPositive();
    }
}
