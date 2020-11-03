package com.epam.esm.exception.service;

import com.epam.esm.exception.ProjectAbstractException;

public class ResourceNotFoundException extends ProjectAbstractException {
    public static final String RESOURCE_NOT_FOUND_ERROR_CODE = "21";

    public ResourceNotFoundException() {
        super(RESOURCE_NOT_FOUND_ERROR_CODE);
    }

    public ResourceNotFoundException(String message) {
        super(message, RESOURCE_NOT_FOUND_ERROR_CODE);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause, RESOURCE_NOT_FOUND_ERROR_CODE);
    }
}
