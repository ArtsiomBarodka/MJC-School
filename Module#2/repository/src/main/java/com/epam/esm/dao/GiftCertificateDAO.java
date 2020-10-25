package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDAO {
    Optional<GiftCertificate> findById(long id);
    void update(@NonNull GiftCertificate giftCertificate);
    long create(@NonNull GiftCertificate giftCertificate);
    void delete(long id);

    List<GiftCertificate> getListGiftCertificatesByTagNameSortByDateAsc(@NonNull String tagName);
    List<GiftCertificate> getListGiftCertificatesByTagNameSortByDateDesc(@NonNull String tagName);
    List<GiftCertificate> getListGiftCertificatesByTagNameSortByNameAsc(@NonNull String tagName);
    List<GiftCertificate> getListGiftCertificatesByTagNameSortByNameDesc(@NonNull String tagName);

    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateAsc(@NonNull String key);
    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateDesc(@NonNull String key);
    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameAsc(@NonNull String key);
    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameDesc(@NonNull String key);
}
