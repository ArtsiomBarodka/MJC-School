package dao;

import entity.GiftCertificate;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDAO {
    Optional<GiftCertificate> findById(long id);
    @NonNull GiftCertificate update(@NonNull GiftCertificate giftCertificate);
    @NonNull GiftCertificate create(@NonNull GiftCertificate giftCertificate);
    void delete(long id);

    List<GiftCertificate> getListGiftCertificatesByTagNameSortByDateAsc(@NonNull String tagName);
    List<GiftCertificate> getListGiftCertificatesByTagNameSortByDateDesc(@NonNull String tagName);
    List<GiftCertificate> getListGiftCertificatesByTagNameSortByNameAsc(@NonNull String tagName);
    List<GiftCertificate> getListGiftCertificatesByTagNameSortByNameDesc(@NonNull String tagName);

    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameSortByDateAsc(@NonNull String giftCertificateName);
    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameSortByDateDesc(@NonNull String giftCertificateName);
    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameSortByNameAsc(@NonNull String giftCertificateName);
    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameSortByNameDesc(@NonNull String giftCertificateName);

    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateAsc(@NonNull String searchingWords);
    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateDesc(@NonNull String searchingWords);
    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameAsc(@NonNull String searchingWords);
    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameDesc(@NonNull String searchingWords);
}
