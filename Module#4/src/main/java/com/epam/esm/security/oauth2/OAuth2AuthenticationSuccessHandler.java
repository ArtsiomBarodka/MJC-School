package com.epam.esm.security.oauth2;

import com.epam.esm.security.jwt.JwtProvider;
import com.epam.esm.util.CookieUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.epam.esm.security.oauth2.OAuth2Constants.REDIRECT_URI_PARAM_COOKIE_NAME;
import static com.epam.esm.security.oauth2.OAuth2Constants.TOKEN_QUERY_PARAMETER;

/**
 * The type O auth 2 authentication success handler.
 */
@Component
@Slf4j
@AllArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final JwtProvider jwtProvider;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            log.info("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        String token = jwtProvider.createToken(getEmailFromAuthentication(authentication));

        return UriComponentsBuilder.fromUriString(redirectUri.orElse(getDefaultTargetUrl()))
                .queryParam(TOKEN_QUERY_PARAMETER, token)
                .build().toUriString();
    }

    /**
     * Clear authentication attributes.
     *
     * @param request  the request
     * @param response the response
     */
    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private String getEmailFromAuthentication(Authentication authentication) {
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        return principal.getAttribute("email");
    }

}