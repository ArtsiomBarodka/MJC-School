package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserService {
    @NonNull User getUserById(@NonNull Long id) throws ResourceNotFoundException;
    @NonNull List<User> listAllUsers(Pageable pageable) throws ResourceNotFoundException;
    @NonNull Long create(@NonNull User user) throws ResourceAlreadyExistException, BadParametersException;
    void update(@NonNull User user, @NonNull Long id) throws ResourceNotFoundException, BadParametersException;
    @NonNull User updateAndReturn(@NonNull User user, @NonNull Long id) throws ResourceNotFoundException, BadParametersException, ServiceException;
}
