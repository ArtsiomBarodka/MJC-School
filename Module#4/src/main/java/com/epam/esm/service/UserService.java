package com.epam.esm.service;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.InnerServiceException;
import com.epam.esm.model.exception.service.ResourceAlreadyExistException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
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
    User getById(@NonNull Long id)
            throws ResourceNotFoundException;

    /**
     * Gets by user name.
     *
     * @param username the username
     * @return the by user name
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    User getByUserName(@NonNull String username)
            throws ResourceNotFoundException;

    /**
     * Gets by user name and password.
     *
     * @param username the username
     * @param password the password
     * @return the by user name and password
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    User getByUserNameAndPassword(@NonNull String username, @NonNull String password)
            throws ResourceNotFoundException;

    /**
     * Gets all.
     *
     * @param pageable the pageable
     * @return the all
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    Page<User> getAll(Pageable pageable)
            throws ResourceNotFoundException;

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
    User update(@NonNull User user, @NonNull Long id)
            throws ResourceNotFoundException, BadParametersException;

    /**
     * Save user.
     *
     * @param user the user
     * @return the user
     * @throws ResourceAlreadyExistException the resource already exist exception
     * @throws InnerServiceException         the inner service exception
     */
    @NonNull
    User save(@NonNull User user)
            throws ResourceAlreadyExistException, InnerServiceException;

    /**
     * Save by username user.
     *
     * @param username the username
     * @return the user
     * @throws InnerServiceException the inner service exception
     */
    @NonNull
    User saveByUsername(@NonNull String username) throws InnerServiceException;

    /**
     * Delete.
     *
     * @param id the id
     * @throws ResourceNotFoundException the resource not found exception
     */
    void delete(@NonNull Long id)
            throws ResourceNotFoundException;

}
