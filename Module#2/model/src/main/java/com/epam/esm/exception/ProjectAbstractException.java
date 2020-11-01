package com.epam.esm.exception;

public abstract class ProjectAbstractException extends Exception{
    public static final long serialVersionUID = 4982720311488402040L;

    private final String errorCode;

    public ProjectAbstractException(String errorCode) {
        this.errorCode = errorCode;
    }

    public ProjectAbstractException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ProjectAbstractException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
