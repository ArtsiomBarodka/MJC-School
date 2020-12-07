package com.epam.esm.dao;

import com.epam.esm.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;

public interface OrderDAO extends PagingAndSortingRepository<Order, Long> {
    Page<Order> getOrdersByUserId(@NonNull Long userId, Pageable pageable);
}
