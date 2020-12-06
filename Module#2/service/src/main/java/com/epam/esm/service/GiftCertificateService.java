package com.epam.esm.service;

import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * The interface Gift certificate service.
 */
public interface GiftCertificateService {
    /**
     * Gets all list gift certificates with tags.
     *
     * @param sortMode the sort mode
     * @return the all list gift certificates with tags
     * @throws ServiceException          the service exception
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    List<GiftCertificate> getAllListGiftCertificatesWithTags(@NonNull SortMode sortMode) throws ServiceException, ResourceNotFoundException;

    /**
     * Gets list gift certificates with tags by tag name.
     *
     * @param tagName  the tag name
     * @param sortMode the sort mode
     * @return the list gift certificates with tags by tag name
     * @throws ServiceException          the service exception
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    List<GiftCertificate> getListGiftCertificatesWithTagsByTagName(@NonNull String tagName, @NonNull SortMode sortMode) throws ServiceException, ResourceNotFoundException;

    /**
     * Gets list gift certificates with tags by search.
     *
     * @param key      the key
     * @param sortMode the sort mode
     * @return the list gift certificates with tags by search
     * @throws ServiceException          the service exception
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    List<GiftCertificate> getListGiftCertificatesWithTagsBySearch(@NonNull String key, @NonNull SortMode sortMode) throws ServiceException, ResourceNotFoundException;

    /**
     * Gets gift certificates by id.
     *
     * @param id the id
     * @return the gift certificates by id
     * @throws ResourceNotFoundException the resource not found exception
     * @throws ServiceException          the service exception
     */
    @NonNull
    GiftCertificate getGiftCertificatesById(@NonNull Long id) throws ResourceNotFoundException, ServiceException;

    /**
     * Create long.
     *
     * @param giftCertificate the gift certificate
     * @return the long
     * @throws ServiceException              the service exception
     * @throws ResourceAlreadyExistException the resource already exist exception
     */
    @NonNull
    Long create(@NonNull GiftCertificate giftCertificate) throws ServiceException, ResourceAlreadyExistException, BadParametersException;

    /**
     * Update gift certificate.
     *
     * @param giftCertificate the gift certificate
     * @param id              the id
     * @return the gift certificate
     * @throws ServiceException          the service exception
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    GiftCertificate update(@NonNull GiftCertificate giftCertificate, @NonNull Long id) throws ServiceException, ResourceNotFoundException, BadParametersException;

    /**
     * Delete.
     *
     * @param id the id
     * @throws ServiceException          the service exception
     * @throws ResourceNotFoundException the resource not found exception
     */
    void delete(@NonNull Long id) throws ServiceException, ResourceNotFoundException;
}
