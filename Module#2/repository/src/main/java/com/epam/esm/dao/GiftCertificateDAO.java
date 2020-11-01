package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.repository.RepositoryException;
import org.springframework.lang.NonNull;
import java.util.List;
import java.util.Optional;

public interface GiftCertificateDAO {
    Optional<GiftCertificate> findById(@NonNull Long id) throws RepositoryException;
    boolean isAlreadyExistByName(@NonNull String  giftCertificateName) throws RepositoryException;
    void update(@NonNull GiftCertificate giftCertificate) throws RepositoryException;
    @NonNull Long create(@NonNull GiftCertificate giftCertificate) throws RepositoryException;
    void delete(@NonNull Long id) throws RepositoryException;

    @NonNull List<GiftCertificate> getListGiftCertificatesByTagNameSortByDateAsc(@NonNull String tagName) throws RepositoryException;
    @NonNull List<GiftCertificate> getListGiftCertificatesByTagNameSortByDateDesc(@NonNull String tagName) throws RepositoryException;
    @NonNull List<GiftCertificate> getListGiftCertificatesByTagNameSortByNameAsc(@NonNull String tagName) throws RepositoryException;
    @NonNull List<GiftCertificate> getListGiftCertificatesByTagNameSortByNameDesc(@NonNull String tagName) throws RepositoryException;

    @NonNull List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateAsc(@NonNull String key) throws RepositoryException;
    @NonNull List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateDesc(@NonNull String key) throws RepositoryException;
    @NonNull List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameAsc(@NonNull String key) throws RepositoryException;
    @NonNull List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameDesc(@NonNull String key) throws RepositoryException;
}
