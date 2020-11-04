package com.epam.esm.exception.service;

import com.epam.esm.exception.ProjectAbstractException;

/**
 * The type Resource already exist exception.
 */
public class ResourceAlreadyExistException extends ProjectAbstractException {
    /**
     * The constant RESOURCE_ALREADY_EXIST_ERROR_CODE.
     */
    public static final String RESOURCE_ALREADY_EXIST_ERROR_CODE = "22";

    /**
     * Instantiates a new Resource already exist exception.
     */
    public ResourceAlreadyExistException() {
        super(RESOURCE_ALREADY_EXIST_ERROR_CODE);
    }

    /**
     * Instantiates a new Resource already exist exception.
     *
     * @param message the message
     */
    public ResourceAlreadyExistException(String message) {
        super(message, RESOURCE_ALREADY_EXIST_ERROR_CODE);
    }

    /**
     * Instantiates a new Resource already exist exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ResourceAlreadyExistException(String message, Throwable cause) {
        super(message, cause, RESOURCE_ALREADY_EXIST_ERROR_CODE);
    }
}
