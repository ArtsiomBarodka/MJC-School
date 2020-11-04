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

/**
 * The type Tag dao impl test.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DataConfiguration.class)
public class TagDAOImplTest {
    private static final String TAG_NAME = "name";

    private TagDAO tagDAO;
    private Tag tag;

    /**
     * Instantiates a new Tag dao impl test.
     *
     * @param testDataSource the test data source
     */
    @Autowired
    public TagDAOImplTest(DataSource testDataSource) {
        tagDAO = new TagDAOImpl(testDataSource);
        tag = new Tag();
        tag.setName(TAG_NAME);
    }

    /**
     * Delete tag in db.
     *
     * @throws RepositoryException the repository exception
     */
    @AfterEach
    void deleteTagInDb() throws RepositoryException {
        tagDAO.delete(tag.getId());
    }

    /**
     * Create tag in db.
     *
     * @throws RepositoryException the repository exception
     */
    @BeforeEach
    void createTagInDb() throws RepositoryException {
        tag.setId(tagDAO.create(tag));
    }


    /**
     * Is already exist by name test should return true.
     *
     * @throws RepositoryException the repository exception
     */
    @Test
    void isAlreadyExistByNameTest_SHOULD_RETURN_TRUE() throws RepositoryException {
        assertThat(tagDAO.isAlreadyExistByName(tag.getName())).isTrue();
    }

    /**
     * Is already exist by name test should return false.
     *
     * @throws RepositoryException the repository exception
     */
    @Test
    void isAlreadyExistByNameTest_SHOULD_RETURN_FALSE() throws RepositoryException {
        assertThat(tagDAO.isAlreadyExistByName(" ")).isFalse();
    }

    /**
     * Create test should create tag and return valid id.
     *
     * @throws RepositoryException the repository exception
     */
    @Test
    void createTest_SHOULD_CREATE_TAG_AND_RETURN_VALID_ID() throws RepositoryException {
        Tag newTag = new Tag();
        newTag.setName("tag");

        assertThat(tagDAO.isAlreadyExistByName(newTag.getName())).isFalse();

        Long newId = tagDAO.create(newTag);

        assertThat(newId).isGreaterThan(0L);
        assertThat(tagDAO.isAlreadyExistByName(newTag.getName())).isTrue();
    }

    /**
     * Find by id test should return valid tag.
     *
     * @throws RepositoryException the repository exception
     */
    @Test
    void findByIdTest_SHOULD_RETURN_VALID_TAG() throws RepositoryException {
        assertThat(tagDAO.findById(tag.getId())).contains(tag);
    }

    /**
     * Find by id test should return empty result.
     *
     * @throws RepositoryException the repository exception
     */
    @Test
    void findByIdTest_SHOULD_RETURN_EMPTY_RESULT() throws RepositoryException {
        Long notExistingTagId = 10L;

        assertThat(tagDAO.findById(notExistingTagId)).isEmpty();
    }

    /**
     * Gets list tags by gift certificate id test should return not empty tags list.
     *
     * @throws RepositoryException the repository exception
     */
    @Test
    void getListTagsByGiftCertificateIdTest_SHOULD_RETURN_NOT_EMPTY_TAGS_LIST() throws RepositoryException {
        Long giftCertificateId = 2L;

        assertThat(tagDAO.getListTagsByGiftCertificateId(giftCertificateId)).isNotEmpty();
    }

    /**
     * Gets list tags by gift certificate id test should return empty result.
     *
     * @throws RepositoryException the repository exception
     */
    @Test
    void getListTagsByGiftCertificateIdTest_SHOULD_RETURN_EMPTY_RESULT() throws RepositoryException {
        Long notExistingGiftCertificateId = 20L;

        assertThat(tagDAO.getListTagsByGiftCertificateId(notExistingGiftCertificateId)).isEmpty();
    }

    /**
     * Delete test should delete tag.
     *
     * @throws RepositoryException the repository exception
     */
    @Test
    void deleteTest_SHOULD_DELETE_TAG() throws RepositoryException {
        assertThat(tagDAO.isAlreadyExistByName(tag.getName())).isTrue();

        tagDAO.delete(tag.getId());

        assertThat(tagDAO.isAlreadyExistByName(tag.getName())).isFalse();
    }
}
