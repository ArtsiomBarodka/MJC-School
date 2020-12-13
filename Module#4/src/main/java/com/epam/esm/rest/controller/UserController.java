package com.epam.esm.rest.controller;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceAlreadyExistException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import com.epam.esm.model.patch.PatchUser;
import com.epam.esm.rest.hateoas.UserAssembler;
import com.epam.esm.security.SecurityProvider;
import com.epam.esm.security.anotation.AdminRole;
import com.epam.esm.security.anotation.AllRoles;
import com.epam.esm.security.anotation.UserRole;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("api/v1/users")
@Validated
public class UserController {
    private final UserService userService;
    private final UserAssembler assembler;
    private final SecurityProvider securityProvider;

    @Autowired
    public UserController(UserService userService, UserAssembler assembler, SecurityProvider securityProvider) {
        this.userService = userService;
        this.assembler = assembler;
        this.securityProvider = securityProvider;
    }

    @AllRoles
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") @Min(1) Long id)
            throws ResourceNotFoundException {

        User user = userService.getById(id);
        return ResponseEntity.ok(assembler.toModel(user));
    }

    @GetMapping("/sign-in")
    public ResponseEntity<User> signInByUsernameAndPassword(@RequestParam @NotEmpty String username,
                                                            @RequestParam @NotEmpty String password, HttpServletResponse response)
            throws ResourceNotFoundException {

        User user = userService.getByUserNameAndPassword(username, password);
        securityProvider.addedNewTokenInHeader(response, user);
        return ResponseEntity.ok(assembler.toModel(user));
    }

    @AllRoles
    @GetMapping
    public ResponseEntity<PagedModel<User>> getAllUsers(Pageable pageable,
                                                        PagedResourcesAssembler<User> pagedResourcesAssembler)
            throws ResourceNotFoundException {

        Page<User> users = userService.getAll(pageable);
        PagedModel<User> pagedModel = pagedResourcesAssembler.toModel(users, assembler);
        pagedModel.add(assembler.getLinksToCollectionModel());
        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody @Valid User user,
                                             UriComponentsBuilder uriComponentsBuilder, HttpServletResponse response)
            throws ResourceAlreadyExistException {
        User savedUser = userService.save(user);
        securityProvider.addedNewTokenInHeader(response, savedUser);

        return ResponseEntity.created(
                uriComponentsBuilder
                        .path("/api/v1/users/{id}")
                        .buildAndExpand(savedUser.getId())
                        .toUri())
                .build();
    }

    @UserRole
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
                            .buildAndExpand(userService.save(user).getId())
                            .toUri())
                    .build();
        }
    }

    @UserRole
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
