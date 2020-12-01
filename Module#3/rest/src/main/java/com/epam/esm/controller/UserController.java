package com.epam.esm.controller;

import com.epam.esm.component.assembler.UserAssembler;
import com.epam.esm.entity.User;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
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

/**
 * The type User controller.
 */
@RestController
@RequestMapping("api/v1/users")
@Validated
public class UserController {
    private final UserService userService;
    private final UserAssembler assembler;

    /**
     * Instantiates a new User controller.
     *
     * @param userService the user service
     * @param assembler   the assembler
     */
    @Autowired
    public UserController(UserService userService, UserAssembler assembler) {
        this.userService = userService;
        this.assembler = assembler;
    }

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        User user = userService.getById(id);
        return ResponseEntity.ok(assembler.toModel(user));
    }

    /**
     * Gets all users.
     *
     * @param pageable                the pageable
     * @param pagedResourcesAssembler the paged resources assembler
     * @return the all users
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping
    public ResponseEntity<PagedModel<User>> getAllUsers(Pageable pageable,
                                                        PagedResourcesAssembler<User> pagedResourcesAssembler)
            throws ResourceNotFoundException {

        Page<User> users = userService.getAll(pageable);
        PagedModel<User> pagedModel = pagedResourcesAssembler.toModel(users, assembler);
        pagedModel.add(assembler.getLinksToCollectionModel(users.getContent()));
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Create user response entity.
     *
     * @param user                 the user
     * @param uriComponentsBuilder the uri components builder
     * @return the response entity
     * @throws BadParametersException        the bad parameters exception
     * @throws ResourceAlreadyExistException the resource already exist exception
     */
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

    /**
     * Update or create user response entity.
     *
     * @param id                   the id
     * @param user                 the user
     * @param uriComponentsBuilder the uri components builder
     * @return the response entity
     * @throws BadParametersException        the bad parameters exception
     * @throws ResourceAlreadyExistException the resource already exist exception
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateOrCreateUser(@PathVariable("id") @Min(1) Long id,
                                                   @RequestBody @Valid User user,
                                                   UriComponentsBuilder uriComponentsBuilder)
            throws BadParametersException, ResourceAlreadyExistException {

        try {
            User updatedUser = userService.update(user, id);
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

    /**
     * Update part of user response entity.
     *
     * @param id        the id
     * @param patchUser the patch user
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     * @throws BadParametersException    the bad parameters exception
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updatePartOfUser(@PathVariable("id") @Min(1) Long id,
                                                   @RequestBody @Valid PatchUser patchUser)
            throws ResourceNotFoundException, BadParametersException {

        User existingUser = userService.getById(id);
        patchUser.mergeToEntity(existingUser);
        User updatedUser = userService.update(existingUser, id);
        return ResponseEntity.ok(assembler.toModel(updatedUser));
    }

}
