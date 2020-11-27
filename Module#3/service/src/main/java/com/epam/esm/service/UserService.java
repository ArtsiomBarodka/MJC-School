package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface UserService {
    @NonNull
    User getById(@NonNull Long id) throws ResourceNotFoundException;

    @NonNull
    Page<User> getAll(Pageable pageable) throws ResourceNotFoundException;

    @NonNull
    Long create(@NonNull User user) throws ResourceAlreadyExistException, BadParametersException;

    @NonNull
    User update(@NonNull User user, @NonNull Long id) throws ResourceNotFoundException, BadParametersException;
}
