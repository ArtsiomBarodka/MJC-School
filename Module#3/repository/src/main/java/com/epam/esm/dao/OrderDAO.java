package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface OrderDAO extends PagingAndSortingRepository<Order, Long> {
    List<Order> getOrdersByUserId(@NonNull Long userId, Pageable pageable);
}
