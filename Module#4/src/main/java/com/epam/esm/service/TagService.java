package com.epam.esm.service;

import com.epam.esm.model.domain.Page;
import com.epam.esm.model.domain.SortMode;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceAlreadyExistException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import org.springframework.lang.NonNull;

import java.util.List;

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
    Long create(@NonNull Tag tag) throws ResourceAlreadyExistException, BadParametersException;

    /**
     * Delete.
     *
     * @param id the id
     * @throws ResourceNotFoundException the resource not found exception
     */
    void delete(@NonNull Long id) throws ResourceNotFoundException;

    /**
     * Gets the most widely used tag of user from the highest cost of all orders.
     *
     * @param userId the user id
     * @return the the most widely used tag of user from the highest cost of all orders
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    Tag getTheMostWidelyUsedTagOfUserFromTheHighestCostOfAllOrders(@NonNull Long userId) throws ResourceNotFoundException;

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    Tag getById(@NonNull Long id) throws ResourceNotFoundException;

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
    Tag update(@NonNull Tag tag, @NonNull Long id) throws ResourceNotFoundException, BadParametersException;

    /**
     * Gets all.
     *
     * @param page     the page
     * @param sortMode the sort mode
     * @return the all
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    List<Tag> getAll(@NonNull Page page, @NonNull SortMode sortMode) throws ResourceNotFoundException;

    /**
     * Gets list by gift certificate id.
     *
     * @param id       the id
     * @param page     the page
     * @param sortMode the sort mode
     * @return the list by gift certificate id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    List<Tag> getListByGiftCertificateId(@NonNull Long id, @NonNull Page page, @NonNull SortMode sortMode) throws ResourceNotFoundException;
}
