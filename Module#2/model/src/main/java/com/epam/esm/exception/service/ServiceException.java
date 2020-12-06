package com.epam.esm.exception.service;

import com.epam.esm.exception.ProjectAbstractException;

/**
 * The type Service exception.
 */
public class ServiceException extends ProjectAbstractException {
    /**
     * The constant SERVICE_ERROR_CODE.
     */
    public static final String SERVICE_ERROR_CODE = "20";

    /**
     * Instantiates a new Service exception.
     */
    public ServiceException() {
        super(SERVICE_ERROR_CODE);
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param message the message
     */
    public ServiceException(String message) {
        super(message, SERVICE_ERROR_CODE);
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause, SERVICE_ERROR_CODE);
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param message   the message
     * @param errorCode the error code
     */
    public ServiceException(String message, String errorCode) {
        super(message, errorCode);
    }

    /**
     * Instantiates a new Service exception.
     *
     * @param message   the message
     * @param cause     the cause
     * @param errorCode the error code
     */
    public ServiceException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }
}

