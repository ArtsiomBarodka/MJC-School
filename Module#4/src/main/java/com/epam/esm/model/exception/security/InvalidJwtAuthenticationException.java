package com.epam.esm.model.exception.security;

import com.epam.esm.model.exception.ProjectAbstractException;

public class InvalidJwtAuthenticationException extends ProjectAbstractException {
    public static final String INVALID_JWT_AUTHENTICATION_ERROR_CODE = "41";

    public InvalidJwtAuthenticationException() {
        super(INVALID_JWT_AUTHENTICATION_ERROR_CODE);
    }

    public InvalidJwtAuthenticationException(String message) {
        super(message, INVALID_JWT_AUTHENTICATION_ERROR_CODE);
    }

    public InvalidJwtAuthenticationException(String message, Throwable cause) {
        super(message, cause, INVALID_JWT_AUTHENTICATION_ERROR_CODE);
    }
}
