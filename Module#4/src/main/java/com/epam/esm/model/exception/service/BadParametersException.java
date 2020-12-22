package com.epam.esm.model.exception.service;


import com.epam.esm.model.exception.ProjectAbstractException;

/**
 * The type Bad parameters exception.
 */
public class BadParametersException extends ProjectAbstractException {
    /**
     * The constant BAD_PARAMETERS_ERROR_CODE.
     */
    public static final String BAD_PARAMETERS_ERROR_CODE = "23";

    /**
     * Instantiates a new Bad parameters exception.
     */
    public BadParametersException() {
        super(BAD_PARAMETERS_ERROR_CODE);
    }

    /**
     * Instantiates a new Bad parameters exception.
     *
     * @param message the message
     */
    public BadParametersException(String message) {
        super(message, BAD_PARAMETERS_ERROR_CODE);
    }

    /**
     * Instantiates a new Bad parameters exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public BadParametersException(String message, Throwable cause) {
        super(message, cause, BAD_PARAMETERS_ERROR_CODE);
    }
}
