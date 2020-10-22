package dao.impl;

import dao.GiftCertificateDAO;
import entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("GiftCertificateDAO")
public class JDBCGiftCertificatesDAO implements GiftCertificateDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCGiftCertificatesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return Optional.empty();
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return null;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesByTagNameSortByDateAsc(String tagName) {
        return null;
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesByTagNameSortByDateDesc(String tagName) {
        return null;
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesByTagNameSortByNameAsc(String tagName) {
        return null;
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesByTagNameSortByNameDesc(String tagName) {
        return null;
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameSortByDateAsc(String giftCertificateName) {
        return null;
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameSortByDateDesc(String giftCertificateName) {
        return null;
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameSortByNameAsc(String giftCertificateName) {
        return null;
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameSortByNameDesc(String giftCertificateName) {
        return null;
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateAsc(String searchingWords) {
        return null;
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateDesc(String searchingWords) {
        return null;
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameAsc(String searchingWords) {
        return null;
    }

    @Override
    public List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameDesc(String searchingWords) {
        return null;
    }



}
