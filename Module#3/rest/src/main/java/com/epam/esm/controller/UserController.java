package com.epam.esm.controller;

import com.epam.esm.entity.User;
import com.epam.esm.exception.service.BadParametersException;
import com.epam.esm.exception.service.ResourceAlreadyExistException;
import com.epam.esm.exception.service.ResourceNotFoundException;
import com.epam.esm.exception.service.ServiceException;
import com.epam.esm.patch.PatchUser;
import com.epam.esm.service.UserService;
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
@RequestMapping("api/v1/users")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable @NotNull @Min(1) Long id)
            throws ResourceNotFoundException {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(Pageable pageable)
            throws ResourceNotFoundException {

        return ResponseEntity.ok(userService.listAllUsers(pageable));
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
            return ResponseEntity.ok(userService.updateAndReturn(user, id));
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
        return ResponseEntity.ok(userService.updateAndReturn(existingUser, id));
    }

}
