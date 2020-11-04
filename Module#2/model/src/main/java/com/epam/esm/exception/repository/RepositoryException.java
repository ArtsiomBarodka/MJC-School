package com.epam.esm.exception.repository;

import com.epam.esm.exception.ProjectAbstractException;

/**
 * The type Repository exception.
 */
public class RepositoryException extends ProjectAbstractException {
    /**
     * The constant REPOSITORY_ERROR_CODE.
     */
    public static final String REPOSITORY_ERROR_CODE = "10";

    /**
     * Instantiates a new Repository exception.
     */
    public RepositoryException() {
        super(REPOSITORY_ERROR_CODE);
    }

    /**
     * Instantiates a new Repository exception.
     *
     * @param message the message
     */
    public RepositoryException(String message) {
        super(message, REPOSITORY_ERROR_CODE);
    }

    /**
     * Instantiates a new Repository exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public RepositoryException(String message, Throwable cause) {
        super(message, cause, REPOSITORY_ERROR_CODE);
    }
}
