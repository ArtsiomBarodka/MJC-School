package com.epam.esm.model.exception.service;

import com.epam.esm.model.exception.ProjectAbstractException;

/**
 * The type Resource not found exception.
 */
public class ResourceNotFoundException extends ProjectAbstractException {
    /**
     * The constant RESOURCE_NOT_FOUND_ERROR_CODE.
     */
    public static final String RESOURCE_NOT_FOUND_ERROR_CODE = "21";

    /**
     * Instantiates a new Resource not found exception.
     */
    public ResourceNotFoundException() {
        super(RESOURCE_NOT_FOUND_ERROR_CODE);
    }

    /**
     * Instantiates a new Resource not found exception.
     *
     * @param message the message
     */
    public ResourceNotFoundException(String message) {
        super(message, RESOURCE_NOT_FOUND_ERROR_CODE);
    }

    /**
     * Instantiates a new Resource not found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause, RESOURCE_NOT_FOUND_ERROR_CODE);
    }
}
