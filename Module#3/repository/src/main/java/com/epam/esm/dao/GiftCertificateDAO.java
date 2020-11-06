package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.repository.RepositoryException;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * The interface Gift certificate dao.
 */
public interface GiftCertificateDAO {
    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws RepositoryException the repository exception
     */
    Optional<GiftCertificate> findById(@NonNull Long id) throws RepositoryException;

    /**
     * Is already exist by name boolean.
     *
     * @param giftCertificateName the gift certificate name
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    boolean isAlreadyExistByName(@NonNull String giftCertificateName) throws RepositoryException;

    /**
     * Update.
     *
     * @param giftCertificate the gift certificate
     * @throws RepositoryException the repository exception
     */
    void update(@NonNull GiftCertificate giftCertificate) throws RepositoryException;

    /**
     * Create long.
     *
     * @param giftCertificate the gift certificate
     * @return the long
     * @throws RepositoryException the repository exception
     */
    @NonNull
    Long create(@NonNull GiftCertificate giftCertificate) throws RepositoryException;

    /**
     * Delete.
     *
     * @param id the id
     * @throws RepositoryException the repository exception
     */
    void delete(@NonNull Long id) throws RepositoryException;

    /**
     * Gets all list gift certificates sort by date asc.
     *
     * @return the all list gift certificates sort by date asc
     * @throws RepositoryException the repository exception
     */
    @NonNull
    List<GiftCertificate> getAllListGiftCertificatesSortByDateAsc() throws RepositoryException;

    /**
     * Gets all list gift certificates sort by date desc.
     *
     * @return the all list gift certificates sort by date desc
     * @throws RepositoryException the repository exception
     */
    @NonNull
    List<GiftCertificate> getAllListGiftCertificatesSortByDateDesc() throws RepositoryException;

    /**
     * Gets all list gift certificates sort by name asc.
     *
     * @return the all list gift certificates sort by name asc
     * @throws RepositoryException the repository exception
     */
    @NonNull
    List<GiftCertificate> getAllListGiftCertificatesSortByNameAsc() throws RepositoryException;

    /**
     * Gets all list gift certificates sort by name desc.
     *
     * @return the all list gift certificates sort by name desc
     * @throws RepositoryException the repository exception
     */
    @NonNull
    List<GiftCertificate> getAllListGiftCertificatesSortByNameDesc() throws RepositoryException;

    /**
     * Gets list gift certificates by tag name sort by date asc.
     *
     * @param tagName the tag name
     * @return the list gift certificates by tag name sort by date asc
     * @throws RepositoryException the repository exception
     */
    @NonNull
    List<GiftCertificate> getListGiftCertificatesByTagNameSortByDateAsc(@NonNull String tagName) throws RepositoryException;

    /**
     * Gets list gift certificates by tag name sort by date desc.
     *
     * @param tagName the tag name
     * @return the list gift certificates by tag name sort by date desc
     * @throws RepositoryException the repository exception
     */
    @NonNull
    List<GiftCertificate> getListGiftCertificatesByTagNameSortByDateDesc(@NonNull String tagName) throws RepositoryException;

    /**
     * Gets list gift certificates by tag name sort by name asc.
     *
     * @param tagName the tag name
     * @return the list gift certificates by tag name sort by name asc
     * @throws RepositoryException the repository exception
     */
    @NonNull
    List<GiftCertificate> getListGiftCertificatesByTagNameSortByNameAsc(@NonNull String tagName) throws RepositoryException;

    /**
     * Gets list gift certificates by tag name sort by name desc.
     *
     * @param tagName the tag name
     * @return the list gift certificates by tag name sort by name desc
     * @throws RepositoryException the repository exception
     */
    @NonNull
    List<GiftCertificate> getListGiftCertificatesByTagNameSortByNameDesc(@NonNull String tagName) throws RepositoryException;

    /**
     * Gets list gift certificates search by gift certificate name or description sort by date asc.
     *
     * @param key the key
     * @return the list gift certificates search by gift certificate name or description sort by date asc
     * @throws RepositoryException the repository exception
     */
    @NonNull
    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateAsc(@NonNull String key) throws RepositoryException;

    /**
     * Gets list gift certificates search by gift certificate name or description sort by date desc.
     *
     * @param key the key
     * @return the list gift certificates search by gift certificate name or description sort by date desc
     * @throws RepositoryException the repository exception
     */
    @NonNull
    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByDateDesc(@NonNull String key) throws RepositoryException;

    /**
     * Gets list gift certificates search by gift certificate name or description sort by name asc.
     *
     * @param key the key
     * @return the list gift certificates search by gift certificate name or description sort by name asc
     * @throws RepositoryException the repository exception
     */
    @NonNull
    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameAsc(@NonNull String key) throws RepositoryException;

    /**
     * Gets list gift certificates search by gift certificate name or description sort by name desc.
     *
     * @param key the key
     * @return the list gift certificates search by gift certificate name or description sort by name desc
     * @throws RepositoryException the repository exception
     */
    @NonNull
    List<GiftCertificate> getListGiftCertificatesSearchByGiftCertificateNameOrDescriptionSortByNameDesc(@NonNull String key) throws RepositoryException;
}
