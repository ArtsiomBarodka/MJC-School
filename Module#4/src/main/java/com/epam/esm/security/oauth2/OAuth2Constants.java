package com.epam.esm.security.oauth2;

/**
 * The type O auth 2 constants.
 */
public final class OAuth2Constants {
    /**
     * The constant OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME.
     */
    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    /**
     * The constant REDIRECT_URI_PARAM_COOKIE_NAME.
     */
    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "oauth2_redirect";
    /**
     * The constant REDIRECT_URI.
     */
    public static final String REDIRECT_URI = "/api/v1/users/oauth2/callback";
    /**
     * The constant TOKEN_QUERY_PARAMETER.
     */
    public static final String TOKEN_QUERY_PARAMETER = "token";
    /**
     * The constant ERROR_QUERY_PARAMETER.
     */
    public static final String ERROR_QUERY_PARAMETER = "error";

    private OAuth2Constants() {
    }
}
