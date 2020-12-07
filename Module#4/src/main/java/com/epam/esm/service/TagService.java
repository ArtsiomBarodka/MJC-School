package com.epam.esm.service;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceAlreadyExistException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface TagService {
    @NonNull
    Long create(@NonNull Tag tag) throws ResourceAlreadyExistException, BadParametersException;

    void delete(@NonNull Long id) throws ResourceNotFoundException;

    @NonNull
    Tag getTheMostWidelyUsedTagOfUsersFromTheHighestCostOfAllOrders() throws ResourceNotFoundException;

    @NonNull
    Tag getById(@NonNull Long id) throws ResourceNotFoundException;

    @NonNull
    Tag update(@NonNull Tag tag, @NonNull Long id) throws ResourceNotFoundException, BadParametersException;

    @NonNull
    Page<Tag> getAll(@NonNull Pageable pageable) throws ResourceNotFoundException;

    @NonNull
    Page<Tag> getListByGiftCertificateId(@NonNull Long id, @NonNull Pageable pageable) throws ResourceNotFoundException;
}
