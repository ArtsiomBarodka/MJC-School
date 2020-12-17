package com.epam.esm.dao;

import com.epam.esm.model.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;


public interface RoleDAO extends CrudRepository<Role, Long> {
    @NonNull Optional<Role> findByName(@NonNull String name);
}
