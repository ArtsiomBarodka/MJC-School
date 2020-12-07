package com.epam.esm.model.exception.service;


import com.epam.esm.model.exception.ProjectAbstractException;

public class ResourceAlreadyExistException extends ProjectAbstractException {
    public static final String RESOURCE_ALREADY_EXIST_ERROR_CODE = "22";

    public ResourceAlreadyExistException() {
        super(RESOURCE_ALREADY_EXIST_ERROR_CODE);
    }

    public ResourceAlreadyExistException(String message) {
        super(message, RESOURCE_ALREADY_EXIST_ERROR_CODE);
    }

    public ResourceAlreadyExistException(String message, Throwable cause) {
        super(message, cause, RESOURCE_ALREADY_EXIST_ERROR_CODE);
    }
}
