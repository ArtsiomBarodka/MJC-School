package com.epam.esm.security.jwt;

import com.epam.esm.security.CustomUserDetails;
import com.epam.esm.security.JpaUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

/**
 * The type Jwt authorization filter.
 */
@Component
@Slf4j
@AllArgsConstructor
public class JwtAuthorizationFilter extends GenericFilter {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final JwtProvider jwtProvider;
    private final JpaUserDetailsService userDetailsService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token != null && jwtProvider.validateToken(token)) {
            String userLogin = jwtProvider.getLoginFromToken(token);
            CustomUserDetails userDetails = userDetailsService.loadUserByUsername(userLogin);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            log.info("Added authentication to securityContextHolder");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith(BEARER)) {
            return bearer.substring(BEARER.length());
        }
        return null;
    }
}
