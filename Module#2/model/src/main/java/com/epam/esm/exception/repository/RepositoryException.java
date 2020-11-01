package com.epam.esm.exception.repository;

import com.epam.esm.exception.ProjectAbstractException;

public class RepositoryException extends ProjectAbstractException {
    public static final String REPOSITORY_ERROR_CODE = "10";
    public static final long serialVersionUID = -1070260743083184518L;

    public RepositoryException() {
        super(REPOSITORY_ERROR_CODE);
    }

    public RepositoryException(String message) {
        super(message, REPOSITORY_ERROR_CODE);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause, REPOSITORY_ERROR_CODE);
    }
}
