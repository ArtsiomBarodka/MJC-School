package com.epam.esm.model.exception.service;

import com.epam.esm.model.exception.ProjectAbstractException;

public class InnerServiceException extends ProjectAbstractException {
    public static final String INNER_SERVICE_ERROR_CODE = "24";

    public InnerServiceException() {
        super(INNER_SERVICE_ERROR_CODE);
    }

    public InnerServiceException(String message) {
        super(message, INNER_SERVICE_ERROR_CODE);
    }

    public InnerServiceException(String message, Throwable cause) {
        super(message, cause, INNER_SERVICE_ERROR_CODE);
    }
}
