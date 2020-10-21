package dao;

import entity.GiftCertificate;
import java.util.List;
import java.util.Optional;

public interface GiftCertificateDAO {
    Optional<GiftCertificate> findById(long id);
    GiftCertificate update(GiftCertificate giftCertificate);
    GiftCertificate create(GiftCertificate giftCertificate);
    void delete(long id);
    List<GiftCertificate> getListCertificatesByTagNameSortByDateAsc(String tagName);
    List<GiftCertificate> getListCertificatesByTagNameSortByDateDesc(String tagName);
    List<GiftCertificate> getListCertificatesByTagNameSortByNameAsc(String tagName);
    List<GiftCertificate> getListCertificatesByTagNameSortByNameDesc(String tagName);
}
