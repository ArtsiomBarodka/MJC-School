package com.epam.esm.rest.controller;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import com.epam.esm.rest.hateoas.OrderAssembler;
import com.epam.esm.security.anotation.AllRoles;
import com.epam.esm.security.anotation.UserRole;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/v1/orders")
@Validated
public class OrderController {
    private final OrderService orderService;
    private final OrderAssembler assembler;

    @Autowired
    public OrderController(OrderService orderService, OrderAssembler assembler) {
        this.orderService = orderService;
        this.assembler = assembler;
    }

    @AllRoles
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        Order order = orderService.getById(id);
        return ResponseEntity.ok(assembler.toModel(order));
    }

    @UserRole
    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody @Valid Order order,
                                              UriComponentsBuilder uriComponentsBuilder)
            throws BadParametersException {

        return ResponseEntity.created(
                uriComponentsBuilder
                        .path("/api/v1/orders/{id}")
                        .buildAndExpand(orderService.create(order))
                        .toUri())
                .build();
    }

    @UserRole
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
