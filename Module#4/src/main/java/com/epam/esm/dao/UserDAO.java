package com.epam.esm.dao;

import com.epam.esm.model.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserDAO extends PagingAndSortingRepository<User, Long> {
    boolean existsByUsername(@NonNull String username);

    Optional<User> findByUsernameAndPassword(@NonNull String username, @NonNull String password);

    Optional<User> findByUsername(@NonNull String username);
}
