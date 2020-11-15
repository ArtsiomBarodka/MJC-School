package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;

public interface OrderService {
    @NonNull
    Long create(@NonNull Order order) throws BadParametersException;

    @NonNull Order getOrderBuId(@NonNull Long id) throws ResourceNotFoundException;

    void delete(@NonNull Long id) throws ResourceNotFoundException;

    @NonNull Order update(@NonNull Order order ,@NonNull Long id) throws ResourceNotFoundException, BadParametersException;

    @NonNull List<Order> listOrdersByUserId(@NonNull Long userId,@NonNull Pageable pageable) throws ResourceNotFoundException;
}
