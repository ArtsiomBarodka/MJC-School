package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The type Order service impl test.
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderDAO orderDAO;
    @Mock
    private UserDAO userDAO;
    @Mock
    private GiftCertificateDAO giftCertificateDAO;
    @InjectMocks
    private OrderServiceImpl orderService;

    /**
     * Create test created order user is not exist.
     */
    @Test
    void createTest_CREATED_ORDER_USER_IS_NOT_EXIST() {
        Order orderMock = mock(Order.class);
        User userMock = mock(User.class);

        when(orderMock.getUser()).thenReturn(userMock);
        when(userMock.getId()).thenReturn(1L);

        when(userDAO.existsById(anyLong()))
                .thenReturn(false);

        assertThrows(BadParametersException.class,
                () -> orderService.create(orderMock));
    }

    /**
     * Create test created order list of gift certificates is empty.
     */
    @Test
    void createTest_CREATED_ORDER_LIST_OF_GIFT_CERTIFICATES_IS_EMPTY() {
        Order orderMock = mock(Order.class);
        User userMock = mock(User.class);

        when(orderMock.getUser()).thenReturn(userMock);
        when(orderMock.getGiftCertificates()).thenReturn(Collections.emptyList());
        when(userMock.getId()).thenReturn(1L);

        when(userDAO.existsById(anyLong()))
                .thenReturn(true);

        assertThrows(BadParametersException.class,
                () -> orderService.create(orderMock));
    }

    /**
     * Create test created order gift certificate is not exist.
     */
    @Test
    void createTest_CREATED_ORDER_GIFT_CERTIFICATE_IS_NOT_EXIST() {
        Order orderMock = mock(Order.class);
        User userMock = mock(User.class);
        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(giftCertificateMock);

        when(orderMock.getUser()).thenReturn(userMock);
        when(orderMock.getGiftCertificates()).thenReturn(giftCertificates);
        when(userMock.getId()).thenReturn(1L);
        when(giftCertificateMock.getId()).thenReturn(1L);

        when(userDAO.existsById(anyLong()))
                .thenReturn(true);

        when(giftCertificateDAO.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(BadParametersException.class,
                () -> orderService.create(orderMock));
    }

    /**
     * Create test should create order.
     *
     * @throws BadParametersException the bad parameters exception
     */
    @Test
    void createTest_SHOULD_CREATE_ORDER() throws BadParametersException {
        Long expected = 1L;

        Order orderMock = mock(Order.class);
        User userMock = mock(User.class);
        GiftCertificate giftCertificateMock = mock(GiftCertificate.class);
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(giftCertificateMock);

        when(orderMock.getUser()).thenReturn(userMock);
        when(orderMock.getGiftCertificates()).thenReturn(giftCertificates);
        when(orderMock.getId()).thenReturn(expected);
        when(userMock.getId()).thenReturn(1L);
        when(giftCertificateMock.getId()).thenReturn(1L);
        when(giftCertificateMock.getPrice()).thenReturn(0D);

        when(userDAO.existsById(anyLong()))
                .thenReturn(true);

        when(giftCertificateDAO.findById(anyLong()))
                .thenReturn(Optional.of(giftCertificateMock));

        when(orderDAO.save(any(Order.class)))
                .thenReturn(orderMock);

        Long actual = orderService.create(orderMock);

        assertEquals(expected, actual);
    }

    /**
     * Gets by id test resource in not exist.
     */
    @Test
    void getByIdTest_RESOURCE_IN_NOT_EXIST() {
        Long id = 1L;

        when(orderDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> orderService.getById(id));
    }

    /**
     * Gets by id test should return order.
     *
     * @throws ResourceNotFoundException the resource not found exception
     */
    @Test
    void getByIdTest_SHOULD_RETURN_ORDER() throws ResourceNotFoundException {
        Order expected = mock(Order.class);

        Long id = 1L;

        when(orderDAO.findById(any(Long.TYPE)))
                .thenReturn(Optional.of(expected));

        Order actual = orderService.getById(id);

        assertEquals(expected, actual);
    }

    /**
     * Delete test order is not exist with id.
     */
    @Test
    void deleteTest_ORDER_IS_NOT_EXIST_WITH_ID() {
        Long id = 1L;

        when(orderDAO.existsById(any(Long.TYPE)))
                .thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> orderService.delete(id));
    }

    /**
     * Gets list by user id resource in not exist.
     */
    @Test
    void getListByUserId_RESOURCE_IN_NOT_EXIST() {
        Long userId = 1L;
        Pageable pageableMock = mock(Pageable.class);

        when(orderDAO.getOrdersByUserId(anyLong(), any(Pageable.class)))
                .thenReturn(Page.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> orderService.getListByUserId(userId, pageableMock));
    }

    /**
     * Gets list by user id should return list.
     *
     * @throws ResourceNotFoundException the resource not found exception
     */
    @Test
    void getListByUserId_SHOULD_RETURN_LIST() throws ResourceNotFoundException {
        Page expected = mock(Page.class);

        Long userId = 1L;
        Pageable pageableMock = mock(Pageable.class);

        when(expected.hasContent()).thenReturn(true);

        when(orderDAO.getOrdersByUserId(anyLong(), any(Pageable.class)))
                .thenReturn(expected);

        Page<Order> actual = orderService.getListByUserId(userId, pageableMock);

        assertIterableEquals(expected, actual);
    }
}
