package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.repository.RepositoryException;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface TagDAO {
    Optional<Tag> findById(@NonNull Long id) throws RepositoryException;

    boolean isAlreadyExistByName(@NonNull String tagName) throws RepositoryException;

    @NonNull
    List<Tag> getListTagsByGiftCertificateId(@NonNull Long id) throws RepositoryException;

    @NonNull
    Long create(@NonNull Tag tag) throws RepositoryException;

    void delete(@NonNull Long id) throws RepositoryException;
}
