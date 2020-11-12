package com.epam.esm.service;

import com.epam.esm.domain.Pageable;
import com.epam.esm.domain.SortMode;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import org.springframework.lang.NonNull;

import java.util.List;

public interface TagService {
    @NonNull
    Long create(@NonNull Tag tag) throws ResourceAlreadyExistException, BadParametersException;

    void delete(@NonNull Long id) throws ResourceNotFoundException;

    @NonNull
    Tag getTagById(@NonNull Long id) throws ResourceNotFoundException;

    @NonNull
    Tag update(@NonNull Tag tag, @NonNull Long id) throws ResourceNotFoundException, BadParametersException;

    @NonNull
    List<Tag> getListAllTagsWithGiftCertificates(@NonNull Pageable pageable, @NonNull SortMode sortMode) throws ResourceNotFoundException;

    @NonNull
    List<Tag> getListTagsWithGiftCertificatesByGiftCertificateId(@NonNull Long id, @NonNull Pageable pageable, @NonNull SortMode sortMode) throws ResourceNotFoundException;
}
