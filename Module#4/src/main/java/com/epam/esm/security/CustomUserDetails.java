package com.epam.esm.security;

import com.epam.esm.model.entity.User;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * The type Custom user details.
 */
@EqualsAndHashCode(callSuper = true)
public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private Long id;

    /**
     * Instantiates a new Custom user details.
     *
     * @param username    the username
     * @param password    the password
     * @param authorities the authorities
     * @param id          the id
     */
    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id) {
        super(username, password, authorities);
        this.id = id;
    }

    /**
     * From user to custom user details custom user details.
     *
     * @param user the user
     * @return the custom user details
     */
    public static CustomUserDetails fromUserToCustomUserDetails(User user) {
        return new CustomUserDetails(user.getUsername(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(r -> new SimpleGrantedAuthority(r.getName()))
                        .collect(Collectors.toList()),
                user.getId());
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }
}
