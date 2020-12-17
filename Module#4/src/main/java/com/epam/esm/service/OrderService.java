package com.epam.esm.service;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface OrderService {
    @NonNull
    Long create(@NonNull Order order)
            throws BadParametersException;

    @NonNull
    Order getById(@NonNull Long id)
            throws ResourceNotFoundException;

    void delete(@NonNull Long id)
            throws ResourceNotFoundException;

    @NonNull
    Page<Order> getListByUserId(@NonNull Long userId, @NonNull Pageable pageable)
            throws ResourceNotFoundException;
}
