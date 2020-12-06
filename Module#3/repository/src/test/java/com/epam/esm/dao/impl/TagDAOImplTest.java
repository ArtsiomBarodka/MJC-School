package com.epam.esm.dao.impl;

import com.epam.esm.TestMain;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.domain.Page;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.Tag;
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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The type Tag dao impl test.
 */
@ActiveProfiles("test")
@Transactional
@Sql({"classpath:dump/insert.sql"})
@SpringBootTest(classes = TestMain.class)
class TagDAOImplTest {
    private static final String TAG_NAME = "name";

    @Autowired
    private TagDAO tagDAO;

    private final Tag tag;

    /**
     * Instantiates a new Tag dao impl test.
     */
    public TagDAOImplTest() {
        tag = new Tag();
        tag.setName(TAG_NAME);
    }

    /**
     * Delete tag in db.
     */
    @AfterEach
    void deleteTagInDb() {
        if (tagDAO.isExistById(tag.getId())) {
            tagDAO.delete(tag.getId());
        }
    }

    /**
     * Create tag in db.
     */
    @BeforeEach
    void createTagInDb() {
        tagDAO.save(tag);
    }

    /**
     * Find by id test should return valid tag.
     */
    @Test
    void findByIdTest_SHOULD_RETURN_VALID_TAG() {
        assertThat(tagDAO.findById(tag.getId())).contains(tag);
    }

    /**
     * Find by id test should return empty result.
     */
    @Test
    void findByIdTest_SHOULD_RETURN_EMPTY_RESULT() {
        Long notExistingTagId = 10L;

        assertThat(tagDAO.findById(notExistingTagId)).isEmpty();
    }

    /**
     * Find the most widely used of user with the highest cost of all orders test should return tag.
     */
    @Test
    void findTheMostWidelyUsedOfUserWithTheHighestCostOfAllOrdersTest_SHOULD_RETURN_TAG() {
        Long notExistingUserId = 1L;

        assertThat(tagDAO.findTheMostWidelyUsedOfUserWithTheHighestCostOfAllOrders(notExistingUserId)).isNotEmpty();
    }

    /**
     * Save test should create tag and return with valid id.
     */
    @Test
    void saveTest_SHOULD_CREATE_TAG_AND_RETURN_WITH_VALID_ID() {
        Tag newTag = new Tag();
        newTag.setName("tag");

        assertThat(tagDAO.isExistByName(newTag.getName())).isFalse();

        Tag repositoryTag = tagDAO.save(newTag);

        assertThat(repositoryTag).isNotNull();
        assertThat(repositoryTag.getId()).isNotNull().isGreaterThan(0L);
        assertThat(tagDAO.isExistByName(newTag.getName())).isTrue();
    }

    /**
     * Save test should update not managed current tag and return.
     */
    @Test
    void saveTest_SHOULD_UPDATE_NOT_MANAGED_CURRENT_TAG_AND_RETURN() {
        String newName = "newName";

        assertThat(tagDAO.isExistByName(newName)).isFalse();

        tag.setName(newName);
        Tag updatedTag = tagDAO.save(tag);

        assertThat(updatedTag).isNotNull();
        assertThat(tagDAO.isExistByName(newName)).isTrue();
    }

    /**
     * Delete test should delete tag.
     */
    @Test
    void deleteTest_SHOULD_DELETE_TAG() {
        assertThat(tagDAO.isExistById(tag.getId())).isTrue();

        tagDAO.delete(tag.getId());

        assertThat(tagDAO.isExistById(tag.getId())).isFalse();
    }

    /**
     * Is exist by id test should return true.
     */
    @Test
    void isExistByIdTest_SHOULD_RETURN_TRUE() {
        assertThat(tagDAO.isExistById(tag.getId())).isTrue();
    }

    /**
     * Is exist by id test should return false.
     */
    @Test
    void isExistByIdTest_SHOULD_RETURN_FALSE() {
        assertThat(tagDAO.isExistById(100L)).isFalse();
    }

    /**
     * Is exist by name test should return true.
     */
    @Test
    void isExistByNameTest_SHOULD_RETURN_TRUE() {
        assertThat(tagDAO.isExistByName(tag.getName())).isTrue();
    }

    /**
     * Is exist by name test should return false.
     */
    @Test
    void isExistByNameTest_SHOULD_RETURN_FALSE() {
        assertThat(tagDAO.isExistByName(" ")).isFalse();
    }

    /**
     * List all test should return not empty tags list.
     *
     * @param sortMode the sort mode
     */
    @ParameterizedTest
    @EnumSource(value = SortMode.class, names = {"ID_ASC", "ID_DESC", "NAME_ASC", "NAME_DESC"})
    void listAllTest_SHOULD_RETURN_NOT_EMPTY_TAGS_LIST(SortMode sortMode) {
        Page page = new Page();

        assertThat(tagDAO.listAll(page, sortMode)).isNotEmpty();
    }

    /**
     * List by gift certificate id test should return not empty tags list.
     *
     * @param sortMode the sort mode
     */
    @ParameterizedTest
    @EnumSource(value = SortMode.class,names = {"ID_ASC", "ID_DESC", "NAME_ASC", "NAME_DESC"})
    void listByGiftCertificateIdTest_SHOULD_RETURN_NOT_EMPTY_TAGS_LIST(SortMode sortMode) {
        Page page = new Page();
        Long existingGiftCertificateId = 1L;

        assertThat(tagDAO.listByGiftCertificateId(existingGiftCertificateId, page, sortMode)).isNotEmpty();
    }

    /**
     * List by gift certificate id test should return empty result.
     *
     * @param sortMode the sort mode
     */
    @ParameterizedTest
    @EnumSource(value = SortMode.class, names = {"ID_ASC", "ID_DESC", "NAME_ASC", "NAME_DESC"})
    void listByGiftCertificateIdTest_SHOULD_RETURN_EMPTY_RESULT(SortMode sortMode) {
        Page page = new Page();
        Long notExistingGiftCertificateId = 20L;

        assertThat(tagDAO.listByGiftCertificateId(notExistingGiftCertificateId, page, sortMode)).isEmpty();
    }

    /**
     * Count all test should return positive count.
     */
    @Test
    void countAllTest_SHOULD_RETURN_POSITIVE_COUNT() {
        assertThat(tagDAO.countAll()).isPositive();
    }

    /**
     * Count by gift certificate id test should return positive count.
     */
    @Test
    void countByGiftCertificateIdTest_SHOULD_RETURN_POSITIVE_COUNT() {
        Long existingGiftCertificateId = 1L;

        assertThat(tagDAO.countByGiftCertificateId(existingGiftCertificateId)).isPositive();
    }

    /**
     * Count by gift certificate id test should return zero count.
     */
    @Test
    void countByGiftCertificateIdTest_SHOULD_RETURN_ZERO_COUNT() {
        Long notExistingGiftCertificateId = 20L;

        assertThat(tagDAO.countByGiftCertificateId(notExistingGiftCertificateId)).isZero();
    }
}
