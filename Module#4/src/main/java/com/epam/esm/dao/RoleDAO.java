package com.epam.esm.dao;

import com.epam.esm.model.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;


/**
 * The interface Role dao.
 */
public interface RoleDAO extends CrudRepository<Role, Long> {
    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    @NonNull Optional<Role> findByName(@NonNull String name);
}
