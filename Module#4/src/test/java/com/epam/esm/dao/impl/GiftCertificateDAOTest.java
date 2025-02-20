package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.model.entity.GiftCertificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The type Gift certificate dao test.
 */
@ActiveProfiles("test")
@Sql({"classpath:dump/insert.sql"})
@DataJpaTest
class GiftCertificateDAOTest {
    private static final String GIFT_CERTIFICATE_NAME = "name";
    private static final String GIFT_CERTIFICATE_DESCRIPTION = "Some description for gift certificate!";
    private static final Double GIFT_CERTIFICATE_PRICE = 100.25;
    private static final Integer GIFT_CERTIFICATE_DURATION = 7;

    @Autowired
    private GiftCertificateDAO giftCertificateDAO;

    private final GiftCertificate giftCertificate;

    /**
     * Instantiates a new Gift certificate dao test.
     */
    public GiftCertificateDAOTest() {
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
        if (giftCertificateDAO.existsById(giftCertificate.getId())) {
            giftCertificateDAO.deleteById(giftCertificate.getId());
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
     * Exists by name test should return true.
     */
    @Test
    void existsByNameTest_SHOULD_RETURN_TRUE() {
        assertThat(giftCertificateDAO.existsByName(giftCertificate.getName())).isTrue();
    }

    /**
     * Exists by name test should return false.
     */
    @Test
    void existsByNameTest_SHOULD_RETURN_FALSE() {
        assertThat(giftCertificateDAO.existsByName(" ")).isFalse();
    }
}
