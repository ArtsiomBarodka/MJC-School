package com.epam.esm.security.oauth2;

public final class OAuth2Constants {
    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "oauth2_redirect";
    public static final String REDIRECT_URI = "/api/v1/users/oauth2/callback";
    public static final String TOKEN_QUERY_PARAMETER = "token";
    public static final String ERROR_QUERY_PARAMETER = "error";

    private OAuth2Constants() {
    }
}
