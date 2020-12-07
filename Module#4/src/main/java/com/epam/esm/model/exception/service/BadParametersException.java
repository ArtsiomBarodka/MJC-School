package com.epam.esm.model.exception.service;


import com.epam.esm.model.exception.ProjectAbstractException;

public class BadParametersException extends ProjectAbstractException {
    public static final String BAD_PARAMETERS_ERROR_CODE = "23";

    public BadParametersException() {
        super(BAD_PARAMETERS_ERROR_CODE);
    }

    public BadParametersException(String message) {
        super(message, BAD_PARAMETERS_ERROR_CODE);
    }

    public BadParametersException(String message, Throwable cause) {
        super(message, cause, BAD_PARAMETERS_ERROR_CODE);
    }
}
