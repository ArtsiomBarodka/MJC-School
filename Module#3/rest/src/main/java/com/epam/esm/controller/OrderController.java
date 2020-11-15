package com.epam.esm.controller;

import com.epam.esm.entity.Order;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        return ResponseEntity.ok(orderService.getOrderBuId(id));
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> getListOrdersByUserId(@RequestParam(value = "id") @NotNull @Min(1) Long id,
                                                             @RequestParam(required = false) Pageable pageable) throws ResourceNotFoundException {

        return ResponseEntity.ok(orderService.listOrdersByUserId(id, pageable));
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrCreateOrder(@PathVariable("id") @Min(1) Long id,
                                                     @RequestBody @Valid Order order,
                                                     UriComponentsBuilder uriComponentsBuilder)
            throws BadParametersException {

        try {
            return ResponseEntity.ok(orderService.update(order, id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.created(
                    uriComponentsBuilder
                            .path("/api/v1/orders/{id}")
                            .buildAndExpand(orderService.create(order))
                            .toUri())
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
