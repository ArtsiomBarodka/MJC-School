package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;

public interface OrderService {
    @NonNull
    Long create(@NonNull Order order) throws BadParametersException;

    @NonNull Order getOrderById(@NonNull Long id) throws ResourceNotFoundException;

    void delete(@NonNull Long id) throws ResourceNotFoundException;

    void update(@NonNull Order order ,@NonNull Long id) throws ResourceNotFoundException, BadParametersException;

    @NonNull Order updateAndReturn(@NonNull Order order ,@NonNull Long id) throws ResourceNotFoundException, BadParametersException, ServiceException;

    @NonNull List<Order> listOrdersByUserId(@NonNull Long userId,@NonNull Pageable pageable) throws ResourceNotFoundException;
}
