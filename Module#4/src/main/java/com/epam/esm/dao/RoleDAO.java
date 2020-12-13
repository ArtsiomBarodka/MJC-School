package com.epam.esm.dao;

import com.epam.esm.model.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;


public interface RoleDAO extends CrudRepository<Role, Long> {
    @Nullable Role findByName(@NonNull String name);
}
