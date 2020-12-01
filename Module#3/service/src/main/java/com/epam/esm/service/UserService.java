package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    User getById(@NonNull Long id) throws ResourceNotFoundException;

    /**
     * Gets all.
     *
     * @param pageable the pageable
     * @return the all
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    Page<User> getAll(Pageable pageable) throws ResourceNotFoundException;

    /**
     * Create long.
     *
     * @param user the user
     * @return the long
     * @throws ResourceAlreadyExistException the resource already exist exception
     * @throws BadParametersException        the bad parameters exception
     */
    @NonNull
    Long create(@NonNull User user) throws ResourceAlreadyExistException, BadParametersException;

    /**
     * Update user.
     *
     * @param user the user
     * @param id   the id
     * @return the user
     * @throws ResourceNotFoundException the resource not found exception
     * @throws BadParametersException    the bad parameters exception
     */
    @NonNull
    User update(@NonNull User user, @NonNull Long id) throws ResourceNotFoundException, BadParametersException;
}
