package dao;

import entity.GiftCertificate;
import java.util.List;
import java.util.Optional;

public interface GiftCertificateDAO {
    Optional<GiftCertificate> findById(long id);
    GiftCertificate update(GiftCertificate giftCertificate);
    GiftCertificate create(GiftCertificate giftCertificate);
    void delete(long id);
    List<GiftCertificate> getListGiftCertificatesByTagNameSortByDateAsc(String tagName);
    List<GiftCertificate> getListGiftCertificatesByTagNameSortByDateDesc(String tagName);
    List<GiftCertificate> getListGiftCertificatesByTagNameSortByNameAsc(String tagName);
    List<GiftCertificate> getListGiftCertificatesByTagNameSortByNameDesc(String tagName);

    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameSortByDateAsc(String giftCertificateName);
    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameSortByDateDesc(String giftCertificateName);
    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameSortByNameAsc(String giftCertificateName);
    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameSortByNameDesc(String giftCertificateName);

    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateAsc(String searchingWords);
    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateDesc(String searchingWords);
    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameAsc(String searchingWords);
    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameDesc(String searchingWords);
}
