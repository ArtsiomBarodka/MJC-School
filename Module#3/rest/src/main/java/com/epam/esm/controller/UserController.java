package com.epam.esm.controller;

import com.epam.esm.component.assembler.UserAssembler;
import com.epam.esm.entity.User;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import com.epam.esm.patch.PatchUser;
import com.epam.esm.service.UserService;
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

@RestController
@RequestMapping("api/v1/users")
@Validated
public class UserController {
    private final UserService userService;
    private final UserAssembler assembler;

    @Autowired
    public UserController(UserService userService, UserAssembler assembler) {
        this.userService = userService;
        this.assembler = assembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        User user = userService.getUserById(id);
        return ResponseEntity.ok(assembler.toModel(user));
    }

    @GetMapping
    public ResponseEntity<PagedModel<User>> getAllUsers(Pageable pageable,
                                                        PagedResourcesAssembler<User> pagedResourcesAssembler)
            throws ResourceNotFoundException {

        Page<User> users = userService.listAllUsers(pageable);
        PagedModel<User> pagedModel = pagedResourcesAssembler.toModel(users, assembler);
        pagedModel.add(assembler.getLinksToCollectionModel(users.getContent()));
        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid User user,
                                             UriComponentsBuilder uriComponentsBuilder)
            throws BadParametersException, ResourceAlreadyExistException {

        return ResponseEntity.created(
                uriComponentsBuilder
                        .path("/api/v1/users/{id}")
                        .buildAndExpand(userService.create(user))
                        .toUri())
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateOrCreateUser(@PathVariable("id") @Min(1) Long id,
                                                   @RequestBody @Valid User user,
                                                   UriComponentsBuilder uriComponentsBuilder)
            throws BadParametersException, ServiceException, ResourceAlreadyExistException {

        try {
            User updatedUser = userService.updateAndReturn(user, id);
            return ResponseEntity.ok(assembler.toModel(updatedUser));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.created(
                    uriComponentsBuilder
                            .path("/api/v1/users/{id}")
                            .buildAndExpand(userService.create(user))
                            .toUri())
                    .build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updatePartOfUser(@PathVariable("id") @Min(1) Long id,
                                                   @RequestBody @Valid PatchUser patchUser)
            throws ResourceNotFoundException, ServiceException, BadParametersException {

        User existingUser = userService.getUserById(id);
        patchUser.mergeToEntity(existingUser);
        User updatedUser = userService.updateAndReturn(existingUser, id);
        return ResponseEntity.ok(assembler.toModel(updatedUser));
    }

}
