package com.epam.esm.controller;

import com.epam.esm.component.assembler.OrderAssembler;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * The type Order controller.
 */
@RestController
@RequestMapping("api/v1/orders")
@Validated
public class OrderController {
    private final OrderService orderService;
    private final OrderAssembler assembler;

    /**
     * Instantiates a new Order controller.
     *
     * @param orderService the order service
     * @param assembler    the assembler
     */
    @Autowired
    public OrderController(OrderService orderService, OrderAssembler assembler) {
        this.orderService = orderService;
        this.assembler = assembler;
    }

    /**
     * Gets order by id.
     *
     * @param id the id
     * @return the order by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        Order order = orderService.getById(id);
        return ResponseEntity.ok(assembler.toModel(order));
    }

    /**
     * Gets list orders by user id.
     *
     * @param id                      the id
     * @param pageable                the pageable
     * @param pagedResourcesAssembler the paged resources assembler
     * @return the list orders by user id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/user")
    public ResponseEntity<PagedModel<Order>> getListOrdersByUserId(@RequestParam(value = "id") @NotNull @Min(1) Long id,
                                                                   Pageable pageable,
                                                                   PagedResourcesAssembler<Order> pagedResourcesAssembler)
            throws ResourceNotFoundException {

        Page<Order> orders = orderService.getListByUserId(id, pageable);
        PagedModel<Order> pagedModel = pagedResourcesAssembler.toModel(orders, assembler);
        pagedModel.add(assembler.getLinksToCollectionModel(orders.getContent()));
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Create order response entity.
     *
     * @param order                the order
     * @param uriComponentsBuilder the uri components builder
     * @return the response entity
     * @throws BadParametersException the bad parameters exception
     */
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

    /**
     * Delete order response entity.
     *
     * @param id the id
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
