package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import org.springframework.lang.NonNull;

/**
 * The interface Tag service.
 */
public interface TagService {
    /**
     * Create long.
     *
     * @param tag the tag
     * @return the long
     * @throws ResourceAlreadyExistException the resource already exist exception
     * @throws ServiceException              the service exception
     */
    @NonNull
    Long create(@NonNull Tag tag) throws ResourceAlreadyExistException, ServiceException, BadParametersException;

    /**
     * Delete.
     *
     * @param id the id
     * @throws ServiceException          the service exception
     * @throws ResourceNotFoundException the resource not found exception
     */
    void delete(@NonNull Long id) throws ServiceException, ResourceNotFoundException;

    /**
     * Gets tag by id.
     *
     * @param id the id
     * @return the tag by id
     * @throws ServiceException          the service exception
     * @throws ResourceNotFoundException the resource not found exception
     */
    @NonNull
    Tag getTagById(@NonNull Long id) throws ServiceException, ResourceNotFoundException;
}
