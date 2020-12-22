package com.epam.esm.model.exception.service;

import com.epam.esm.model.exception.ProjectAbstractException;

/**
 * The type Inner service exception.
 */
public class InnerServiceException extends ProjectAbstractException {
    /**
     * The constant INNER_SERVICE_ERROR_CODE.
     */
    public static final String INNER_SERVICE_ERROR_CODE = "24";

    /**
     * Instantiates a new Inner service exception.
     */
    public InnerServiceException() {
        super(INNER_SERVICE_ERROR_CODE);
    }

    /**
     * Instantiates a new Inner service exception.
     *
     * @param message the message
     */
    public InnerServiceException(String message) {
        super(message, INNER_SERVICE_ERROR_CODE);
    }

    /**
     * Instantiates a new Inner service exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public InnerServiceException(String message, Throwable cause) {
        super(message, cause, INNER_SERVICE_ERROR_CODE);
    }
}
