package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import com.epam.esm.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Order service.
 */
@Slf4j
@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO;
    private UserDAO userDAO;
    private GiftCertificateDAO giftCertificateDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public @NonNull Long create(Order order) throws BadParametersException {
        //check if current user exists in repository
        if (!userDAO.existsById(order.getUser().getId())) {
            log.warn("User with id {} is not exist", order.getUser().getId());
            throw new BadParametersException(String.format("User with id %d is not exist", order.getUser().getId()));
        }

        List<GiftCertificate> certificates = order.getGiftCertificates();

        //check if list of gift certificates exists and isn`t empty
        if (certificates == null || certificates.isEmpty()) {
            log.warn("Order must contains at least 1 gift certificate");
            throw new BadParametersException("Order must contains at least 1 gift certificate");
        }

        //check if list of gift certificates exists and count sum of all certificates price
        double sumPrice = 0;
        for (GiftCertificate giftCertificate : certificates) {
            sumPrice += giftCertificateDAO.findById(giftCertificate.getId())
                    .orElseThrow(() -> {
                        log.warn("Gift certificate with id {} is not exist", giftCertificate.getId());
                        return new BadParametersException(String.format("Gift certificate with id %d is not exist", giftCertificate.getId()));
                    })
                    .getPrice();
        }
        order.setSumPrice(sumPrice);

        //save to repository and return id;
        return orderDAO.save(order).getId();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Order getById(Long id) throws ResourceNotFoundException {
        return orderDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Order with id %d is not exist", id)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws ResourceNotFoundException {
        if(!orderDAO.existsById(id)){
            log.warn("Order with id {} is not exist", id);
            throw new ResourceNotFoundException(String.format("Order with id %d is not exist", id));
        }
        orderDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Page<Order> getListByUserId(Long userId, Pageable pageable) throws ResourceNotFoundException {
        Page<Order> orders = orderDAO.getOrdersByUserId(userId, pageable);
        if (!orders.hasContent()) {
            log.warn("List of orders are not found");
            throw new ResourceNotFoundException("List of orders are not found");
        }
        return orders;
    }
}
