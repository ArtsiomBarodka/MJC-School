package com.epam.esm.service;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceAlreadyExistException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

/**
 * The interface Tag service.
 */
public interface TagService {
    /**
     * Create long.
     *
     * @param tag the tag
     * @return the long
     * @throws ResourceAlreadyExistException the resource already exist exception
     * @throws BadParametersException        the bad parameters exception
     */
    @NonNull
    Long create(@NonNull Tag tag)
            throws ResourceAlreadyExistException, BadParametersException;

    /**
     * Delete.
     *
     * @param id the id
     * @throws ResourceNotFoundException the resource not found exception
     */
    void delete(@NonNull Long id)
            throws ResourceNotFoundException;

    /**
     * Gets the most widely used tag of users from the highest cost of all orders.
     *
     * @return the the most widely used tag of users from the highest cost of all orders
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    Tag getTheMostWidelyUsedTagOfUsersFromTheHighestCostOfAllOrders()
            throws ResourceNotFoundException;

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    Tag getById(@NonNull Long id)
            throws ResourceNotFoundException;

    /**
     * Update tag.
     *
     * @param tag the tag
     * @param id  the id
     * @return the tag
     * @throws ResourceNotFoundException the resource not found exception
     * @throws BadParametersException    the bad parameters exception
     */
    @NonNull
    Tag update(@NonNull Tag tag, @NonNull Long id)
            throws ResourceNotFoundException, BadParametersException;

    /**
     * Gets all.
     *
     * @param pageable the pageable
     * @return the all
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    Page<Tag> getAll(@NonNull Pageable pageable)
            throws ResourceNotFoundException;

    /**
     * Gets list by gift certificate id.
     *
     * @param id       the id
     * @param pageable the pageable
     * @return the list by gift certificate id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    Page<Tag> getListByGiftCertificateId(@NonNull Long id, @NonNull Pageable pageable)
            throws ResourceNotFoundException;
}
