package com.epam.esm.dao;

import com.epam.esm.model.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;

/**
 * The interface User dao.
 */
public interface UserDAO extends PagingAndSortingRepository<User, Long> {
    /**
     * Exists by name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    boolean existsByName(@NonNull String name);
}
