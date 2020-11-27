package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserDAO userDAO;
    @Mock
    private OrderDAO orderDAO;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getByIdTest_RESOURCE_IN_NOT_EXIST() {
        Long id = 1L;

        when(userDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.getById(id));
    }

    @Test
    void getByIdTest_SHOULD_RETURN_USER() throws ResourceNotFoundException {
        User expected = mock(User.class);

        Long id = 1L;

        when(userDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(expected));

        User actual = userService.getById(id);

        assertEquals(expected, actual);
    }

    @Test
    void getAllTest_RESOURCE_IN_NOT_EXIST() {
        Pageable pageableMock = mock(Pageable.class);

        when(userDAO.findAll(any(Pageable.class)))
                .thenReturn(Page.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.getAll(pageableMock));
    }

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

    @Test
    void createTest_USER_ALREADY_EXIST_WITH_NAME() {
        User userMock = mock(User.class);

        when(userMock.getName()).thenReturn("");

        when(userDAO.existsByName(anyString()))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistException.class,
                () -> userService.create(userMock));
    }

    @Test
    void createTest_CURRENT_ORDER_IS_NOT_EXIST() {
        User userMock = mock(User.class);
        Order orderMock = mock(Order.class);
        List<Order> orders = new ArrayList<>();
        orders.add(orderMock);

        when(userMock.getName()).thenReturn("");
        when(userMock.getOrders()).thenReturn(orders);
        when(orderMock.getId()).thenReturn(1L);

        when(userDAO.existsByName(anyString()))
                .thenReturn(false);

        when(orderDAO.existsById(anyLong()))
                .thenReturn(false);

        assertThrows(BadParametersException.class,
                () -> userService.create(userMock));
    }

    @Test
    void createTest_SHOULD_CREATE_USER() throws ResourceAlreadyExistException, BadParametersException {
        Long expected = 1L;

        User userMock = mock(User.class);
        Order orderMock = mock(Order.class);
        List<Order> orders = new ArrayList<>();
        orders.add(orderMock);

        when(userMock.getName()).thenReturn("");
        when(userMock.getOrders()).thenReturn(orders);
        when(userMock.getId()).thenReturn(expected);
        when(orderMock.getId()).thenReturn(1L);

        when(userDAO.existsByName(anyString()))
                .thenReturn(false);

        when(orderDAO.existsById(anyLong()))
                .thenReturn(true);

        when(userDAO.save(any(User.class)))
                .thenReturn(userMock);

        Long actual = userService.create(userMock);

        assertEquals(expected, actual);
    }

    @Test
    void updateTest_USER_IS_NOT_EXIST_WITH_ID() {
        Long id = 1L;
        User userMock = mock(User.class);

        when(userDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.update(userMock, id));
    }

    @Test
    void updateTest_USER_UPDATED_NAME_ALREADY_EXIST() {
        Long id = 1L;
        User userMock = mock(User.class);
        User repositoryUserMock = mock(User.class);

        when(userMock.getName()).thenReturn("name");
        when(repositoryUserMock.getName()).thenReturn("anotherName");

        when(userDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(repositoryUserMock));

        when(userDAO.existsByName(anyString()))
                .thenReturn(true);

        assertThrows(BadParametersException.class,
                () -> userService.update(userMock, id));
    }

    @Test
    void updateTest_SHOULD_UPDATE_USER() throws ResourceNotFoundException, BadParametersException {
        Long id = 1L;
        User userMock = mock(User.class);

        when(userMock.getName()).thenReturn("name");

        when(userDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(userMock));

        when(userDAO.existsByName(anyString()))
                .thenReturn(false);

        User actual = userService.update(userMock, id);

        assertNotNull(actual);
    }
}
