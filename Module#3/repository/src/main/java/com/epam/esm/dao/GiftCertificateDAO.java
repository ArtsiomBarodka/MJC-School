package com.epam.esm.dao;

import com.epam.esm.domain.Pageable;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * The interface Gift certificate dao.
 */
public interface GiftCertificateDAO {
    @NonNull
    Optional<GiftCertificate> findById(@NonNull Long id);

    @NonNull
    GiftCertificate save(@NonNull GiftCertificate giftCertificate);

    void delete(@NonNull Long id);

    boolean isExistById(@NonNull Long id);

    boolean isExistByName(@NonNull String name);

    @NonNull
    List<GiftCertificate> listAllGiftCertificatesSortByIdAsc(@NonNull Pageable pageable);

    @NonNull
    List<GiftCertificate> listAllGiftCertificatesSortByIdDesc(@NonNull Pageable pageable);

    @NonNull
    List<GiftCertificate> listAllGiftCertificatesSortByNameAsc(@NonNull Pageable pageable);

    @NonNull
    List<GiftCertificate> listAllGiftCertificatesSortByNameDesc(@NonNull Pageable pageable);

    @NonNull
    List<GiftCertificate> listAllGiftCertificatesSortByDateAsc(@NonNull Pageable pageable);

    @NonNull
    List<GiftCertificate> listAllGiftCertificatesSortByDateDesc(@NonNull Pageable pageable);

    @NonNull
    List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByIdAsc(@NonNull List<String> tagNames, @NonNull Pageable pageable);

    @NonNull
    List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByIdDesc(@NonNull List<String> tagNames, @NonNull Pageable pageable);

    @NonNull
    List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByNameAsc(@NonNull List<String> tagNames, @NonNull Pageable pageable);

    @NonNull
    List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByNameDesc(@NonNull List<String> tagNames, @NonNull Pageable pageable);

    @NonNull
    List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByDateAsc(@NonNull List<String> tagNames, @NonNull Pageable pageable);

    @NonNull
    List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByDateDesc(@NonNull List<String> tagNames, @NonNull Pageable pageable);

    Long allGiftCertificatesCount();

    Long allGiftCertificatesByTagNamesCount(List<String> tagNames);
}


