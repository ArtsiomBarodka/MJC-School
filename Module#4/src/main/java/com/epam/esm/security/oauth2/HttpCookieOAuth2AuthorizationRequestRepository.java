package com.epam.esm.security.oauth2;

import com.epam.esm.util.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.esm.security.oauth2.OAuth2Constants.*;

/**
 * The type Http cookie o auth 2 authorization request repository.
 */
@Slf4j
@Component
public class HttpCookieOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    @Value("${oauth2.cookie.expired.seconds}")
    private int cookieExpireSeconds;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return CookieUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
            removeAuthorizationRequestCookies(request, response);
            return;
        }
        CookieUtils.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, CookieUtils.serialize(authorizationRequest), cookieExpireSeconds);
        CookieUtils.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, REDIRECT_URI, cookieExpireSeconds);
        log.info("Cookies were added to response");
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return this.loadAuthorizationRequest(request);
    }

    /**
     * Remove authorization request cookies.
     *
     * @param request  the request
     * @param response the response
     */
    protected void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
        log.info("Cookies were deleted from request");
    }
}