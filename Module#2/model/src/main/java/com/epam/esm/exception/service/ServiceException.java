package com.epam.esm.exception.service;

import com.epam.esm.exception.ProjectAbstractException;

public class ServiceException extends ProjectAbstractException {
    public static final String SERVICE_ERROR_CODE = "20";

    public ServiceException() {
        super(SERVICE_ERROR_CODE);
    }

    public ServiceException(String message) {
        super(message, SERVICE_ERROR_CODE);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause, SERVICE_ERROR_CODE);
    }

    public ServiceException(String message, String errorCode) {
        super(message, errorCode);
    }

    public ServiceException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }
}

