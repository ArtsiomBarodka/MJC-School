package com.epam.esm.exception;

/**
 * The type Project abstract exception.
 */
public abstract class ProjectAbstractException extends Exception {

    private final String errorCode;

    /**
     * Instantiates a new Project abstract exception.
     *
     * @param errorCode the error code
     */
    public ProjectAbstractException(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Instantiates a new Project abstract exception.
     *
     * @param message   the message
     * @param errorCode the error code
     */
    public ProjectAbstractException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Instantiates a new Project abstract exception.
     *
     * @param message   the message
     * @param cause     the cause
     * @param errorCode the error code
     */
    public ProjectAbstractException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Gets error code.
     *
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }
}
