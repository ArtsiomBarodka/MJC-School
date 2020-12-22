package com.epam.esm.rest.controller;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import com.epam.esm.model.request.OrderRequest;
import com.epam.esm.model.view.OrderView;
import com.epam.esm.rest.hateoas.OrderAssembler;
import com.epam.esm.security.annotation.AdminRole;
import com.epam.esm.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * The type Order controller.
 */
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/orders")
@Validated
public class OrderController {
    private final OrderService orderService;
    private final OrderAssembler assembler;

    /**
     * Gets list orders by user id.
     *
     * @param id                      the id
     * @param pageable                the pageable
     * @param pagedResourcesAssembler the paged resources assembler
     * @return the list orders by user id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') or @securityProvider.hasUserId(authentication,#id)")
    @GetMapping("/user")
    public ResponseEntity<PagedModel<OrderView>> getListOrdersByUserId(@RequestParam(value = "id") @NotNull @Min(1) Long id,
                                                                       @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable,
                                                                       PagedResourcesAssembler<Order> pagedResourcesAssembler)
            throws ResourceNotFoundException {

        Page<Order> orders = orderService.getListByUserId(id, pageable);
        PagedModel<OrderView> pagedModel = pagedResourcesAssembler.toModel(orders, assembler);
        pagedModel.add(assembler.getLinksToCollectionModel());
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Gets order by id.
     *
     * @param id the id
     * @return the order by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @AdminRole
    @GetMapping("/{id}")
    public ResponseEntity<OrderView> getOrderById(@PathVariable @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        Order order = orderService.getById(id);
        return ResponseEntity.ok(assembler.toModel(order));
    }


    /**
     * Create order response entity.
     *
     * @param orderRequest         the order request
     * @param uriComponentsBuilder the uri components builder
     * @return the response entity
     * @throws BadParametersException the bad parameters exception
     */
    @PreAuthorize("@securityProvider.hasUserId(authentication,#orderRequest.userId)")
    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody @Valid OrderRequest orderRequest,
                                              UriComponentsBuilder uriComponentsBuilder)
            throws BadParametersException {

        return ResponseEntity.created(
                uriComponentsBuilder
                        .path("/api/v1/orders/{id}")
                        .buildAndExpand(orderService.create(OrderRequest.toOrder(orderRequest)))
                        .toUri())
                .build();
    }
}
