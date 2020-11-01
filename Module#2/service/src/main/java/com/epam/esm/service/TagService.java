package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import org.springframework.lang.NonNull;

public interface TagService {
    boolean isAlreadyExist(@NonNull String tagName) throws ServiceException;
    @NonNull Long create(@NonNull Tag tag) throws ResourceAlreadyExistException, ServiceException;
    void delete(@NonNull Long id) throws ServiceException, ResourceNotFoundException;
    @NonNull Tag getTagById(@NonNull Long id) throws ServiceException, ResourceNotFoundException;
}
