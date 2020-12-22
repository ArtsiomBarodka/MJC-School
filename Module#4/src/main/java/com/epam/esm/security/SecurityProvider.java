package com.epam.esm.security;

import com.epam.esm.model.entity.User;
import com.epam.esm.security.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * The type Security provider.
 */
@AllArgsConstructor
@Component
public final class SecurityProvider {
    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;

    /**
     * Added new token in header.
     *
     * @param response the response
     * @param user     the user
     */
    public void addedNewTokenInHeader(HttpServletResponse response, User user) {
        String token = jwtProvider.createToken(user.getUsername());
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }

    /**
     * Has user id boolean.
     *
     * @param authentication the authentication
     * @param userId         the user id
     * @return the boolean
     */
    public boolean hasUserId(Authentication authentication, Long userId) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getId().equals(userId);
    }

    /**
     * Get username from token optional.
     *
     * @param token the token
     * @return the optional
     */
    public Optional<String> getUsernameFromToken(String token){
        if(token != null && jwtProvider.validateToken(token)){
            return Optional.ofNullable(jwtProvider.getLoginFromToken(token));
        }
        return Optional.empty();
    }
}
