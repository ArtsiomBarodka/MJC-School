package com.epam.esm.rest.controller;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.InnerServiceException;
import com.epam.esm.model.exception.service.ResourceAlreadyExistException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import com.epam.esm.model.request.PatchUser;
import com.epam.esm.model.request.SignInRequest;
import com.epam.esm.model.request.SignUpRequest;
import com.epam.esm.model.view.UserView;
import com.epam.esm.rest.hateoas.UserAssembler;
import com.epam.esm.security.SecurityProvider;
import com.epam.esm.security.annotation.AdminRole;
import com.epam.esm.security.annotation.AllRoles;
import com.epam.esm.service.UserService;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Optional;

import static com.epam.esm.security.oauth2.OAuth2Constants.ERROR_QUERY_PARAMETER;
import static com.epam.esm.security.oauth2.OAuth2Constants.TOKEN_QUERY_PARAMETER;

/**
 * The type User controller.
 */
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/users")
@Validated
public class UserController {
    private final UserService userService;
    private final UserAssembler assembler;
    private final SecurityProvider securityProvider;

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') or @securityProvider.hasUserId(authentication,#id)")
    @GetMapping("/{id}")
    public ResponseEntity<UserView> getById(@PathVariable("id") @Min(1) Long id)
            throws ResourceNotFoundException {

        User user = userService.getById(id);
        return ResponseEntity.ok(assembler.toModel(user));
    }

    /**
     * Sign in by username and password response entity.
     *
     * @param signInRequest the sign in request
     * @param response      the response
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @PostMapping("/sign-in")
    public ResponseEntity<UserView> signInByUsernameAndPassword(@RequestBody @Valid SignInRequest signInRequest,
                                                                HttpServletResponse response)
            throws ResourceNotFoundException {

        User user = userService.getByUserNameAndPassword(signInRequest.getUsername(), signInRequest.getPassword());
        securityProvider.addedNewTokenInHeader(response, user);
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
    @AdminRole
    @GetMapping
    public ResponseEntity<PagedModel<UserView>> getAllUsers(@PageableDefault(size = Integer.MAX_VALUE) Pageable pageable,
                                                            PagedResourcesAssembler<User> pagedResourcesAssembler)
            throws ResourceNotFoundException {

        Page<User> users = userService.getAll(pageable);
        PagedModel<UserView> pagedModel = pagedResourcesAssembler.toModel(users, assembler);
        pagedModel.add(assembler.getLinksToCollectionModel());
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Sign up response entity.
     *
     * @param signUpRequest        the sign up request
     * @param uriComponentsBuilder the uri components builder
     * @param response             the response
     * @return the response entity
     * @throws ResourceAlreadyExistException the resource already exist exception
     * @throws InnerServiceException         the inner service exception
     */
    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody @Valid SignUpRequest signUpRequest,
                                         UriComponentsBuilder uriComponentsBuilder,
                                         HttpServletResponse response)
            throws ResourceAlreadyExistException, InnerServiceException {
        User savedUser = userService.save(SignUpRequest.toUser(signUpRequest));
        securityProvider.addedNewTokenInHeader(response, savedUser);

        return ResponseEntity.created(
                uriComponentsBuilder
                        .path("/api/v1/users/{id}")
                        .buildAndExpand(savedUser.getId())
                        .toUri())
                .build();
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or @securityProvider.hasUserId(authentication,#id)")
    @PatchMapping("/{id}")
    public ResponseEntity<UserView> updatePartOfUser(@PathVariable("id") @Min(1) Long id,
                                                     @RequestBody @Valid PatchUser patchUser)
            throws ResourceNotFoundException, BadParametersException {

        User existingUser = userService.getById(id);
        patchUser.mergeToEntity(existingUser);
        User updatedUser = userService.update(existingUser, id);
        return ResponseEntity.ok(assembler.toModel(updatedUser));
    }

    /**
     * Sign in by social response entity.
     *
     * @param token                the token
     * @param error                the error
     * @param uriComponentsBuilder the uri components builder
     * @param response             the response
     * @return the response entity
     * @throws InnerServiceException the inner service exception
     */
    @GetMapping("/oauth2/callback")
    public ResponseEntity<Object> signInBySocial(@RequestParam(value = TOKEN_QUERY_PARAMETER, required = false) String token,
                                                 @RequestParam(value = ERROR_QUERY_PARAMETER, required = false) String error,
                                                 UriComponentsBuilder uriComponentsBuilder,
                                                 HttpServletResponse response) throws InnerServiceException {

        if(error!= null){
            return ResponseEntity.badRequest().body(error);
        }

        Optional<String> username = securityProvider.getUsernameFromToken(token);
        if (username.isPresent()) {
            try {
                User user = userService.getByUserName(username.get());
                securityProvider.addedNewTokenInHeader(response, user);
                return ResponseEntity.ok(assembler.toModel(user));

            } catch (ResourceNotFoundException e) {
                User savedUser = userService.saveByUsername(username.get());
                securityProvider.addedNewTokenInHeader(response, savedUser);

                return ResponseEntity.created(
                        uriComponentsBuilder
                                .path("/api/v1/users/{id}")
                                .buildAndExpand(savedUser.getId())
                                .toUri())
                        .build();
            }

        }
        return ResponseEntity.badRequest().build();
    }
}
