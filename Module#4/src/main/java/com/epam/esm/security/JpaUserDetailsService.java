package com.epam.esm.security;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.model.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * The type Jpa user details service.
 */
@AllArgsConstructor
@Component
public class JpaUserDetailsService implements UserDetailsService {
    private final UserDAO userDAO;

    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        User user = userDAO.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can`t find user by username"));

        return CustomUserDetails.fromUserToCustomUserDetails(user);
    }
}
