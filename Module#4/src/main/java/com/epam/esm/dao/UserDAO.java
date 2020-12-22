package com.epam.esm.dao;

import com.epam.esm.model.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * The interface User dao.
 */
public interface UserDAO extends PagingAndSortingRepository<User, Long> {
    /**
     * Exists by username boolean.
     *
     * @param username the username
     * @return the boolean
     */
    boolean existsByUsername(@NonNull String username);

    /**
     * Find by username optional.
     *
     * @param username the username
     * @return the optional
     */
    Optional<User> findByUsername(@NonNull String username);
}
