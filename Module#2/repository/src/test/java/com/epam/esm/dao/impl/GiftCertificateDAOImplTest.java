package com.epam.esm.dao.impl;

import com.epam.esm.configuration.DataConfiguration;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.repository.RepositoryException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DataConfiguration.class)
public class GiftCertificateDAOImplTest {
    private static final String GIFT_CERTIFICATE_NAME = "name";
    private static final String GIFT_CERTIFICATE_DESCRIPTION = "Some description for gift certificate!";
    private static final Double GIFT_CERTIFICATE_PRICE = 100.25;
    private static final Integer GIFT_CERTIFICATE_DURATION = 7;

    private GiftCertificateDAO giftCertificateDAO;
    private GiftCertificate giftCertificate;

    @Autowired
    public GiftCertificateDAOImplTest(DataSource testDataSource) {
        giftCertificateDAO = new GiftCertificatesDAOImpl(testDataSource);
        giftCertificate = new GiftCertificate();
        giftCertificate.setName(GIFT_CERTIFICATE_NAME);
        giftCertificate.setDescription(GIFT_CERTIFICATE_DESCRIPTION);
        giftCertificate.setPrice(GIFT_CERTIFICATE_PRICE);
        giftCertificate.setDuration(GIFT_CERTIFICATE_DURATION);
    }

    @AfterEach
    void deleteTagInDb() throws RepositoryException {
        giftCertificateDAO.delete(giftCertificate.getId());
    }

    @BeforeEach
    void createTagInDb() throws RepositoryException {
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));
    }

    @Test
    void isAlreadyExistByNameTest_SHOULD_RETURN_TRUE() throws RepositoryException {
        assertThat(giftCertificateDAO.isAlreadyExistByName(giftCertificate.getName())).isTrue();
    }

    @Test
    void isAlreadyExistByNameTest_SHOULD_RETURN_FALSE() throws RepositoryException {
        assertThat(giftCertificateDAO.isAlreadyExistByName(" ")).isFalse();
    }

    @Test
    void createTest_SHOULD_CREATE_GIFT_CERTIFICATE_AND_RETURN_VALID_ID() throws RepositoryException {
        GiftCertificate newGiftCertificate = new GiftCertificate();
        newGiftCertificate.setName("giftCertificate");
        newGiftCertificate.setDescription("some desc");
        newGiftCertificate.setPrice(22.2);
        newGiftCertificate.setDuration(8);

        assertThat(giftCertificateDAO.isAlreadyExistByName(newGiftCertificate.getName())).isFalse();

        Long newId = giftCertificateDAO.create(newGiftCertificate);

        assertThat(newId).isGreaterThan(0L);
        assertThat(giftCertificateDAO.isAlreadyExistByName(newGiftCertificate.getName())).isTrue();
    }

    @Test
    void findByIdTest_SHOULD_RETURN_VALID_GIFT_CERTIFICATE() throws RepositoryException {
        Optional<GiftCertificate> repositoryGiftCertificate = giftCertificateDAO.findById(giftCertificate.getId());

        assertThat(repositoryGiftCertificate).isPresent();
        assertThat(repositoryGiftCertificate.get()).hasFieldOrProperty(giftCertificate.getName());
    }

    @Test
    void findByIdTest_SHOULD_RETURN_EMPTY_RESULT() throws RepositoryException {
        Long notExistingTagId = 10L;

        assertThat(giftCertificateDAO.findById(notExistingTagId)).isEmpty();
    }

    @Test
    void deleteTest_SHOULD_DELETE_GIFT_CERTIFICATE() throws RepositoryException {
        assertThat(giftCertificateDAO.isAlreadyExistByName(giftCertificate.getName())).isTrue();

        giftCertificateDAO.delete(giftCertificate.getId());

        assertThat(giftCertificateDAO.isAlreadyExistByName(giftCertificate.getName())).isFalse();
    }

    @Test
    void updateTest_SHOULD_UPDATE_GIFT_CERTIFICATE() throws RepositoryException {
        String newName = "newName";

        assertThat(giftCertificateDAO.isAlreadyExistByName(newName)).isFalse();

        giftCertificate.setName(newName);
        giftCertificateDAO.update(giftCertificate);

        assertThat(giftCertificateDAO.isAlreadyExistByName(newName)).isTrue();
    }

    @Test
    void getAllListGiftCertificatesSortByDateAscTest_SHOULD_RETURN_NOT_EMPTY_GIFT_CERTIFICATES_LIST() throws RepositoryException {
        assertThat(giftCertificateDAO.getAllListGiftCertificatesSortByDateAsc()).isNotEmpty();
    }

    @Test
    void getAllListGiftCertificatesSortByDateDescTest_SHOULD_RETURN_EMPTY_RESULT() throws RepositoryException {
        assertThat(giftCertificateDAO.getAllListGiftCertificatesSortByDateDesc()).isNotEmpty();
    }

    @Test
    void getAllListGiftCertificatesSortByNameAscTest_SHOULD_RETURN_NOT_EMPTY_GIFT_CERTIFICATES_LIST() throws RepositoryException {
        assertThat(giftCertificateDAO.getAllListGiftCertificatesSortByNameAsc()).isNotEmpty();
    }

    @Test
    void getAllListGiftCertificatesSortByNameDescTest_SHOULD_RETURN_EMPTY_RESULT() throws RepositoryException {
        assertThat(giftCertificateDAO.getAllListGiftCertificatesSortByNameDesc()).isNotEmpty();
    }

    @Test
    void getListGiftCertificatesByTagNameSortByDateAscTest_SHOULD_RETURN_NOT_EMPTY_GIFT_CERTIFICATES_LIST() throws RepositoryException {
        String tagName = "one";

        assertThat(giftCertificateDAO.getListGiftCertificatesByTagNameSortByDateAsc(tagName)).isNotEmpty();
    }

    @Test
    void getListGiftCertificatesByTagNameSortByDateAscTest_SHOULD_RETURN_EMPTY_RESULT() throws RepositoryException {
        String notExistingTagName = " ";

        assertThat(giftCertificateDAO.getListGiftCertificatesByTagNameSortByDateAsc(notExistingTagName)).isEmpty();
    }

    @Test
    void getListGiftCertificatesByTagNameSortByDateDescTest_SHOULD_RETURN_NOT_EMPTY_GIFT_CERTIFICATES_LIST() throws RepositoryException {
        String tagName = "one";

        assertThat(giftCertificateDAO.getListGiftCertificatesByTagNameSortByDateDesc(tagName)).isNotEmpty();
    }

    @Test
    void getListGiftCertificatesByTagNameSortByDateDescTest_SHOULD_RETURN_EMPTY_RESULT() throws RepositoryException {
        String notExistingTagName = " ";

        assertThat(giftCertificateDAO.getListGiftCertificatesByTagNameSortByDateDesc(notExistingTagName)).isEmpty();
    }

    @Test
    void getListGiftCertificatesByTagNameSortByNameDescTest_SHOULD_RETURN_NOT_EMPTY_GIFT_CERTIFICATES_LIST() throws RepositoryException {
        String tagName = "one";

        assertThat(giftCertificateDAO.getListGiftCertificatesByTagNameSortByNameDesc(tagName)).isNotEmpty();
    }

    @Test
    void getListGiftCertificatesByTagNameSortByNameDescTest_SHOULD_RETURN_EMPTY_RESULT() throws RepositoryException {
        String notExistingTagName = " ";

        assertThat(giftCertificateDAO.getListGiftCertificatesByTagNameSortByNameDesc(notExistingTagName)).isEmpty();
    }

    @Test
    void getListGiftCertificatesByTagNameSortByNameAscTest_SHOULD_RETURN_NOT_EMPTY_GIFT_CERTIFICATES_LIST() throws RepositoryException {
        String tagName = "one";

        assertThat(giftCertificateDAO.getListGiftCertificatesByTagNameSortByNameAsc(tagName)).isNotEmpty();
    }

    @Test
    void getListGiftCertificatesByTagNameSortByNameAscTest_SHOULD_RETURN_EMPTY_RESULT() throws RepositoryException {
        String notExistingTagName = " ";

        assertThat(giftCertificateDAO.getListGiftCertificatesByTagNameSortByNameAsc(notExistingTagName)).isEmpty();
    }
}
