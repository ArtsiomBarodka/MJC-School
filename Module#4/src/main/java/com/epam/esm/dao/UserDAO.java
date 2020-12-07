package com.epam.esm.dao;

import com.epam.esm.model.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;

public interface UserDAO extends PagingAndSortingRepository<User, Long> {
    boolean existsByName(@NonNull String name);
}
