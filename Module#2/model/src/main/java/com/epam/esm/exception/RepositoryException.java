package com.epam.esm.exception;

public class RepositoryException extends Exception{
    public static final long serialVersionUID = -1070260743083184518L;

    public RepositoryException() {
    }

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
