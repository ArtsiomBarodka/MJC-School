package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import com.epam.esm.service.OrderService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private GiftCertificateDAO giftCertificateDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public @NonNull Long create(Order order) throws BadParametersException {
        //check if current user exists in repository
        if (!userDAO.existsById(order.getUser().getId())) {
            LOGGER.warn("User with id {} is not exist", order.getUser().getId());
            throw new BadParametersException(String.format("User with id %d is not exist", order.getUser().getId()));
        }

        //check if list of gift certificates exist and count sum of all certificates price
        double sumPrice = 0;
        for (GiftCertificate giftCertificate : order.getGiftCertificates()) {
            sumPrice += giftCertificateDAO.findById(giftCertificate.getId())
                    .orElseThrow(() -> {
                        LOGGER.warn("Gift certificate with id {} is not exist", giftCertificate.getId());
                        return new BadParametersException(String.format("Gift certificate with id %d is not exist", giftCertificate.getId()));
                    })
                    .getPrice();
        }
        order.setSumPrice(sumPrice);

        //save to repository and return id;
        return orderDAO.save(order).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) throws ResourceNotFoundException {
        return orderDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Order with id %d is not exist", id)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws ResourceNotFoundException {
        if(!orderDAO.existsById(id)){
            LOGGER.warn("Order with id {} is not exist", id);
            throw new ResourceNotFoundException(String.format("Order with id %d is not exist", id));
        }
        orderDAO.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Order order, Long id) throws ResourceNotFoundException, BadParametersException {
        //check if current order exists in repository
        Order repositoryOrder = orderDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Order with id %d is not exist", id)));

        //check if current user exists in repository
        if (!userDAO.existsById(order.getUser().getId())) {
            LOGGER.warn("User with id {} is not exist", order.getUser().getId());
            throw new BadParametersException(String.format("User with id %d is not exist", order.getUser().getId()));
        }

        //check if list of gift certificates exist and count sum of all certificates price
        double sumPrice = 0;
        for (GiftCertificate giftCertificate : order.getGiftCertificates()) {
            sumPrice += giftCertificateDAO.findById(giftCertificate.getId())
                    .orElseThrow(() -> {
                        LOGGER.warn("Gift certificate with id {} is not exist", giftCertificate.getId());
                        return new BadParametersException(String.format("Gift certificate with id %d is not exist", giftCertificate.getId()));
                    })
                    .getPrice();
        }

        //update order
        repositoryOrder.setSumPrice(sumPrice);
        repositoryOrder.setUser(order.getUser());
        repositoryOrder.setGiftCertificates(order.getGiftCertificates());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order updateAndReturn(Order order, Long id)
            throws ResourceNotFoundException, BadParametersException, ServiceException {
        update(order, id);
        return orderDAO.findById(id)
                .orElseThrow(() -> new ServiceException("Can`t find updated order by id after update"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> listOrdersByUserId(Long userId, Pageable pageable) throws ResourceNotFoundException {
        List<Order> orders = orderDAO.getOrdersByUserId(userId, pageable);
        if (orders == null) {
            LOGGER.warn("List of orders are not found");
            throw new ResourceNotFoundException("List of orders are not found");
        }
        return orders;
    }
}
