package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private OrderDAO orderDAO;

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) throws ResourceNotFoundException {
        return userDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("User with id %d is not exist", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listAllUsers(Pageable pageable) {
        return (List<User>) userDAO.findAll(pageable);
    }

    @Override
    @Transactional
    public Long create(User user) throws ResourceAlreadyExistException, BadParametersException {
        if (userDAO.existsByName(user.getName())) {
            LOGGER.warn("User with name {} already exist", user.getName());
            throw new ResourceAlreadyExistException(String.format("User with name %s already exist", user.getName()));
        }

        for (Order order : user.getOrders()){
            if(!orderDAO.existsById(order.getId())){
                LOGGER.warn("Order with id {} is not exist", order.getId());
                throw new BadParametersException(String.format("Order with id %d is already exist", order.getId()));
            }
        }

        return userDAO.save(user).getId();
    }

    @Override
    @Transactional
    public User update(User user, Long id) throws ResourceNotFoundException, BadParametersException {
        User repositoryUser = userDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id %d is not exist", id)));

        if (!user.getName().equals(repositoryUser.getName()) && userDAO.existsByName(user.getName())) {
            LOGGER.warn("User with name {} is already exist", user.getName());
            throw new BadParametersException(String.format("User with name %s is already exist", user.getName()));
        }

        for (Order order : user.getOrders()){
            if(!orderDAO.existsById(order.getId())){
                LOGGER.warn("Order with id {} is not exist", order.getId());
                throw new BadParametersException(String.format("Order with id %d is already exist", order.getId()));
            }
        }

        repositoryUser.setName(user.getName());
        repositoryUser.setOrders(user.getOrders());

        return repositoryUser;
    }
}
