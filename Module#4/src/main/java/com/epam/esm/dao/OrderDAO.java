package com.epam.esm.dao;

import com.epam.esm.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;

/**
 * The interface Order dao.
 */
public interface OrderDAO extends PagingAndSortingRepository<Order, Long> {
    /**
     * Gets orders by user id.
     *
     * @param userId   the user id
     * @param pageable the pageable
     * @return the orders by user id
     */
    Page<Order> getOrdersByUserId(@NonNull Long userId, Pageable pageable);
}
