package com.epam.esm.service;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceAlreadyExistException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * The interface Gift certificate service.
 */
public interface GiftCertificateService {

    /**
     * Gets all.
     *
     * @param pageable the pageable
     * @return the all
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    Page<GiftCertificate> getAll(@NonNull Pageable pageable)
            throws ResourceNotFoundException;

    /**
     * Gets list by tag names.
     *
     * @param names    the names
     * @param pageable the pageable
     * @return the list by tag names
     * @throws ResourceNotFoundException the resource not found exception
     * @throws BadParametersException    the bad parameters exception
     */
    @NonNull
    Page<GiftCertificate> getListByTagNames(@NonNull List<String> names,
                                            @NonNull Pageable pageable)
            throws ResourceNotFoundException, BadParametersException;

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    GiftCertificate getById(@NonNull Long id)
            throws ResourceNotFoundException;

    /**
     * Create long.
     *
     * @param giftCertificate the gift certificate
     * @return the long
     * @throws ResourceAlreadyExistException the resource already exist exception
     * @throws BadParametersException        the bad parameters exception
     */
    @NonNull
    Long create(@NonNull GiftCertificate giftCertificate)
            throws ResourceAlreadyExistException, BadParametersException;

    /**
     * Update gift certificate.
     *
     * @param giftCertificate the gift certificate
     * @param id              the id
     * @return the gift certificate
     * @throws ResourceNotFoundException the resource not found exception
     * @throws BadParametersException    the bad parameters exception
     */
    @NonNull
    GiftCertificate update(@NonNull GiftCertificate giftCertificate, @NonNull Long id)
            throws ResourceNotFoundException, BadParametersException;

    /**
     * Delete.
     *
     * @param id the id
     * @throws ResourceNotFoundException the resource not found exception
     */
    void delete(@NonNull Long id)
            throws ResourceNotFoundException;
}
