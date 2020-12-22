package com.epam.esm.service;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

/**
 * The interface Order service.
 */
public interface OrderService {
    /**
     * Create long.
     *
     * @param order the order
     * @return the long
     * @throws BadParametersException the bad parameters exception
     */
    @NonNull
    Long create(@NonNull Order order)
            throws BadParametersException;

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    Order getById(@NonNull Long id)
            throws ResourceNotFoundException;

    /**
     * Delete.
     *
     * @param id the id
     * @throws ResourceNotFoundException the resource not found exception
     */
    void delete(@NonNull Long id)
            throws ResourceNotFoundException;

    /**
     * Gets list by user id.
     *
     * @param userId   the user id
     * @param pageable the pageable
     * @return the list by user id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    Page<Order> getListByUserId(@NonNull Long userId, @NonNull Pageable pageable)
            throws ResourceNotFoundException;
}
