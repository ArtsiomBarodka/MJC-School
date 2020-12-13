package com.epam.esm.service.impl;

import com.epam.esm.dao.RoleDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceAlreadyExistException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User getById(Long id) throws ResourceNotFoundException {
        return userDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id %d is not exist", id)));
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Page<User> getAll(Pageable pageable) throws ResourceNotFoundException {
        Page<User> users = userDAO.findAll(pageable);
        if (!users.hasContent()) {
            log.warn("List of users are not found");
            throw new ResourceNotFoundException("List of users are not found");
        }
        return users;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User update(User user, Long id) throws ResourceNotFoundException, BadParametersException {
        User repositoryUser = userDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id %d is not exist", id)));

        if (userDAO.existsByUsername(user.getUsername())
                && !user.getUsername().equalsIgnoreCase(repositoryUser.getUsername())) {
            log.warn("User with username {} is already exist", user.getUsername());
            throw new BadParametersException(String.format("User with username %s is already exist", user.getUsername()));
        }

        repositoryUser.setUsername(user.getUsername());
        repositoryUser.setPassword(passwordEncoder.encode(user.getPassword()));
        repositoryUser.setFirstName(user.getFirstName());
        repositoryUser.setLastName(user.getLastName());

        return repositoryUser;
    }

    @Override
    public User getByUserName(String username) {
        return null;
    }

    @Override
    public User getByUserNameAndPassword(String username, String password) throws ResourceNotFoundException {
        return userDAO.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new ResourceNotFoundException("User  is not exist"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User save(User user) throws ResourceAlreadyExistException {
        if (userDAO.existsByUsername(user.getUsername())) {
            log.warn("User with username {} already exist", user.getUsername());
            throw new ResourceAlreadyExistException(String.format("User with username %s already exist", user.getUsername()));
        }

        Role role = roleDAO.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);

        User savedUser = userDAO.save(user);

        log.info("User {} successfully saved", savedUser);

        return savedUser;
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        userDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id %d is not exist", id)));

        userDAO.deleteById(id);
    }
}
