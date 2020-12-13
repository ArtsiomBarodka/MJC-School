package com.epam.esm.security;

import com.epam.esm.model.entity.User;
import com.epam.esm.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
@Component
public final class SecurityProvider {
    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    private JwtProvider jwtProvider;

    public void addedNewTokenInHeader(HttpServletResponse response, User user){
        String token = jwtProvider.createToken(user.getUsername());
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }

    public boolean hasUserId(Authentication authentication, Long userId) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getId().equals(userId);
    }
}
