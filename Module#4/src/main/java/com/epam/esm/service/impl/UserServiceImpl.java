package com.epam.esm.service.impl;

import com.epam.esm.dao.RoleDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.exception.service.InnerServiceException;
import com.epam.esm.model.exception.service.ResourceAlreadyExistException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private static final String TEMPORARY_FIRST_NAME = "TemporaryFirstName";
    private static final String TEMPORARY_LAST_NAME = "TemporaryLastName";

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) throws ResourceNotFoundException {
        return userDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id %d is not exist", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUserName(String username) throws ResourceNotFoundException {
        return userDAO.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with username %s is not exist", username)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User saveByUsername(String username) throws InnerServiceException {
        Role role = roleDAO.findByName("ROLE_USER")
                .orElseThrow(() -> new InnerServiceException("Can`t find user role in repository"));

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        User user = new User();
        user.setPassword(passwordEncoder.encode(RandomStringUtils.randomAlphabetic(20)));
        user.setUsername(username);
        user.setFirstName(TEMPORARY_FIRST_NAME);
        user.setLastName(TEMPORARY_LAST_NAME);
        user.setRoles(roles);

        User savedUser = userDAO.save(user);

        log.info("User {} successfully saved", savedUser);

        return savedUser;
    }

    @Override
    @Transactional(readOnly = true)
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
    public User update(User user, Long id) throws ResourceNotFoundException {
        User repositoryUser = userDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id %d is not exist", id)));

        repositoryUser.setFirstName(user.getFirstName());
        repositoryUser.setLastName(user.getLastName());

        return repositoryUser;
    }

    @Override
    public User getByUserNameAndPassword(String username, String password) throws ResourceNotFoundException {
        User user = userDAO.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User is not exist"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new ResourceNotFoundException("User is not exist");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User save(User user) throws ResourceAlreadyExistException, InnerServiceException {
        if (userDAO.existsByUsername(user.getUsername())) {
            log.warn("User with username {} already exist", user.getUsername());
            throw new ResourceAlreadyExistException(String.format("User with username %s already exist", user.getUsername()));
        }

        Role role = roleDAO.findByName("ROLE_USER")
                .orElseThrow(() -> new InnerServiceException("Can`t find user role in repository"));

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
        if(!userDAO.existsById(id)){
            throw new ResourceNotFoundException(String.format("User with id %d is not exist", id));
        }
        userDAO.deleteById(id);
    }
}
