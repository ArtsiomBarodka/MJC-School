package com.epam.esm.service.impl;

import com.epam.esm.dao.RoleDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.exception.service.InnerServiceException;
import com.epam.esm.model.exception.service.ResourceAlreadyExistException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * The type User service impl test.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserDAO userDAO;
    @Mock
    private RoleDAO roleDAO;
    /**
     * The Password encoder.
     */
    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    /**
     * Gets by id test resource in not exist.
     */
    @Test
    void getByIdTest_RESOURCE_IN_NOT_EXIST() {
        Long id = 1L;

        when(userDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.getById(id));
    }

    /**
     * Gets by id test should return user.
     *
     * @throws ResourceNotFoundException the resource not found exception
     */
    @Test
    void getByIdTest_SHOULD_RETURN_USER() throws ResourceNotFoundException {
        User expected = mock(User.class);

        Long id = 1L;

        when(userDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(expected));

        User actual = userService.getById(id);

        assertEquals(expected, actual);
    }

    /**
     * Gets by user name test resource in not exist.
     */
    @Test
    void getByUserNameTest_RESOURCE_IN_NOT_EXIST() {
        String notExistingUsername = "username";

        when(userDAO.findByUsername(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.getByUserName(notExistingUsername));
    }

    /**
     * Gets by user name test should return user.
     *
     * @throws ResourceNotFoundException the resource not found exception
     */
    @Test
    void getByUserNameTest_SHOULD_RETURN_USER() throws ResourceNotFoundException {
        User expected = mock(User.class);

        String existingUsername = "username";

        when(userDAO.findByUsername(anyString()))
                .thenReturn(Optional.of(expected));

        User actual = userService.getByUserName(existingUsername);

        assertEquals(expected, actual);
    }

    /**
     * Gets by user name and password test resource in not exist by username.
     */
    @Test
    void getByUserNameAndPasswordTest_RESOURCE_IN_NOT_EXIST_BY_USERNAME() {
        String notExistingUsername = "username";
        String password = "password";

        when(userDAO.findByUsername(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.getByUserNameAndPassword(notExistingUsername, password));
    }

    /**
     * Gets by user name and password test password is not equals.
     */
    @Test
    void getByUserNameAndPasswordTest_PASSWORD_IS_NOT_EQUALS() {
        User userMock = mock(User.class);

        String existingUsername = "username";
        String notExistingPassword = "password";

        when(userMock.getPassword()).thenReturn("");

        when(userDAO.findByUsername(anyString()))
                .thenReturn(Optional.of(userMock));

        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> userService.getByUserNameAndPassword(existingUsername, notExistingPassword));
    }

    /**
     * Gets by user name and password test should return user.
     *
     * @throws ResourceNotFoundException the resource not found exception
     */
    @Test
    void getByUserNameAndPasswordTest_SHOULD_RETURN_USER() throws ResourceNotFoundException {
        User expected = mock(User.class);

        String existingUsername = "username";
        String existingPassword = "password";

        when(expected.getPassword()).thenReturn("");

        when(userDAO.findByUsername(anyString()))
                .thenReturn(Optional.of(expected));

        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(true);

        User actual = userService.getByUserNameAndPassword(existingUsername, existingPassword);

        assertEquals(expected, actual);
    }

    /**
     * Gets all test resource in not exist.
     */
    @Test
    void getAllTest_RESOURCE_IN_NOT_EXIST() {
        Pageable pageableMock = mock(Pageable.class);

        when(userDAO.findAll(any(Pageable.class)))
                .thenReturn(Page.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.getAll(pageableMock));
    }

    /**
     * Gets all test should return list.
     *
     * @throws ResourceNotFoundException the resource not found exception
     */
    @Test
    void getAllTest_SHOULD_RETURN_LIST() throws ResourceNotFoundException {
        Page expected = mock(Page.class);
        Pageable pageableMock = mock(Pageable.class);

        when(expected.hasContent()).thenReturn(true);

        when(userDAO.findAll(any(Pageable.class)))
                .thenReturn(expected);

        Page<User> actual = userService.getAll(pageableMock);

        assertIterableEquals(expected, actual);
    }

    /**
     * Save test user already exist with username.
     */
    @Test
    void saveTest_USER_ALREADY_EXIST_WITH_USERNAME() {
        User userMock = mock(User.class);

        when(userMock.getUsername()).thenReturn("");

        when(userDAO.existsByUsername(anyString()))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistException.class,
                () -> userService.save(userMock));
    }

    /**
     * Save test user role not exist in repository.
     */
    @Test
    void saveTest_USER_ROLE_NOT_EXIST_IN_REPOSITORY() {
        User userMock = mock(User.class);

        when(userMock.getUsername()).thenReturn("");

        when(userDAO.existsByUsername(anyString()))
                .thenReturn(false);

        when(roleDAO.findByName(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(InnerServiceException.class,
                () -> userService.save(userMock));
    }

    /**
     * Save test should create user.
     *
     * @throws ResourceAlreadyExistException the resource already exist exception
     * @throws InnerServiceException         the inner service exception
     */
    @Test
    void saveTest_SHOULD_CREATE_USER() throws ResourceAlreadyExistException, InnerServiceException {
        Long expected = 1L;

        User userMock = mock(User.class);
        Role roleMock = mock(Role.class);

        when(userMock.getUsername()).thenReturn("");
        when(userMock.getPassword()).thenReturn("");
        when(userMock.getId()).thenReturn(expected);

        when(userDAO.existsByUsername(anyString()))
                .thenReturn(false);

        when(roleDAO.findByName(anyString()))
                .thenReturn(Optional.of(roleMock));

        when(userDAO.save(any(User.class)))
                .thenReturn(userMock);

        Long actual = userService.save(userMock).getId();

        assertEquals(expected, actual);
    }

    /**
     * Save by username test user role not exist in repository.
     */
    @Test
    void saveByUsernameTest_USER_ROLE_NOT_EXIST_IN_REPOSITORY() {
        String username = "";

        when(roleDAO.findByName(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(InnerServiceException.class,
                () -> userService.saveByUsername(username));
    }

    /**
     * Save by username test should create user.
     *
     * @throws InnerServiceException the inner service exception
     */
    @Test
    void saveByUsernameTest_SHOULD_CREATE_USER() throws InnerServiceException {
        Long expected = 1L;

        String username = "";
        Role roleMock = mock(Role.class);
        User userMock = mock(User.class);

        when(userMock.getId()).thenReturn(expected);

        when(roleDAO.findByName(anyString()))
                .thenReturn(Optional.of(roleMock));

        when(userDAO.save(any(User.class)))
                .thenReturn(userMock);

        Long actual = userService.saveByUsername(username).getId();

        assertEquals(expected, actual);
    }

    /**
     * Update test user is not exist with id.
     */
    @Test
    void updateTest_USER_IS_NOT_EXIST_WITH_ID() {
        Long id = 1L;
        User userMock = mock(User.class);

        when(userDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.update(userMock, id));
    }


    /**
     * Update test should update user.
     *
     * @throws ResourceNotFoundException the resource not found exception
     */
    @Test
    void updateTest_SHOULD_UPDATE_USER() throws ResourceNotFoundException {
        Long id = 1L;
        User userMock = mock(User.class);

        when(userMock.getFirstName()).thenReturn("");
        when(userMock.getLastName()).thenReturn("");

        when(userDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(userMock));

        User actual = userService.update(userMock, id);

        assertNotNull(actual);
    }

    /**
     * Delete test user is not exist with id.
     */
    @Test
    void deleteTest_USER_IS_NOT_EXIST_WITH_ID() {
        Long id = 1L;

        when(userDAO.existsById(any(Long.TYPE)))
                .thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> userService.delete(id));
    }
}
