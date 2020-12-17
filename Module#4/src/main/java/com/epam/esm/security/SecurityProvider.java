package com.epam.esm.security;

import com.epam.esm.model.entity.User;
import com.epam.esm.security.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@AllArgsConstructor
@Component
public final class SecurityProvider {
    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;

    public void addedNewTokenInHeader(HttpServletResponse response, User user) {
        String token = jwtProvider.createToken(user.getUsername());
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }

    public boolean hasUserId(Authentication authentication, Long userId) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getId().equals(userId);
    }

    public Optional<String> getUsernameFromToken(String token){
        if(token != null && jwtProvider.validateToken(token)){
            return Optional.ofNullable(jwtProvider.getLoginFromToken(token));
        }
        return Optional.empty();
    }
}
