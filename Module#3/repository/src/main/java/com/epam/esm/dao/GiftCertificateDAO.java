package com.epam.esm.dao;

import com.epam.esm.domain.Page;
import com.epam.esm.domain.SortMode;
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
    List<GiftCertificate> listAllGiftCertificates(@NonNull Page page, SortMode sortMode);

    @NonNull
    List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByIdAsc(@NonNull List<String> tagNames, @NonNull Page page);

    @NonNull
    List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByIdDesc(@NonNull List<String> tagNames, @NonNull Page page);

    @NonNull
    List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByNameAsc(@NonNull List<String> tagNames, @NonNull Page page);

    @NonNull
    List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByNameDesc(@NonNull List<String> tagNames, @NonNull Page page);

    @NonNull
    List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByDateAsc(@NonNull List<String> tagNames, @NonNull Page page);

    @NonNull
    List<GiftCertificate> listAllGiftCertificatesByTagNamesSortByDateDesc(@NonNull List<String> tagNames, @NonNull Page page);

    Long allGiftCertificatesCount();

    Long allGiftCertificatesByTagNamesCount(List<String> tagNames);

    List<GiftCertificate> criteriaListByNames(Page page, SortMode sortMode, List<String> tagNames);
}


