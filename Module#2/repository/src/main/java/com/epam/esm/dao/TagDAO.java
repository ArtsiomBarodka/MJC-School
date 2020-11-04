package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.repository.RepositoryException;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag dao.
 */
public interface TagDAO {
    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws RepositoryException the repository exception
     */
    Optional<Tag> findById(@NonNull Long id) throws RepositoryException;

    /**
     * Is already exist by name boolean.
     *
     * @param tagName the tag name
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    boolean isAlreadyExistByName(@NonNull String tagName) throws RepositoryException;

    /**
     * Gets list tags by gift certificate id.
     *
     * @param id the id
     * @return the list tags by gift certificate id
     * @throws RepositoryException the repository exception
     */
    @NonNull
    List<Tag> getListTagsByGiftCertificateId(@NonNull Long id) throws RepositoryException;

    /**
     * Create long.
     *
     * @param tag the tag
     * @return the long
     * @throws RepositoryException the repository exception
     */
    @NonNull
    Long create(@NonNull Tag tag) throws RepositoryException;

    /**
     * Delete.
     *
     * @param id the id
     * @throws RepositoryException the repository exception
     */
    void delete(@NonNull Long id) throws RepositoryException;
}
