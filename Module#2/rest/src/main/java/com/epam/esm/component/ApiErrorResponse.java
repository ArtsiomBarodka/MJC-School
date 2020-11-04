package com.epam.esm.component;

import org.springframework.http.HttpStatus;

/**
 * The type Api error response.
 */
public class ApiErrorResponse {
    private HttpStatus statusCode;
    private String message;
    private String errorCode;

    /**
     * Instantiates a new Api error response.
     */
    public ApiErrorResponse() {
    }

    /**
     * Instantiates a new Api error response.
     *
     * @param statusCode the status code
     * @param message    the message
     * @param errorCode  the error code
     */
    public ApiErrorResponse(HttpStatus statusCode, String message, String errorCode) {
        this.statusCode = statusCode;
        this.message = message;
        this.errorCode = errorCode;
    }

    /**
     * Gets status code.
     *
     * @return the status code
     */
    public HttpStatus getStatusCode() {
        return statusCode;
    }

    /**
     * Sets status code.
     *
     * @param statusCode the status code
     */
    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets error code.
     *
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets error code.
     *
     * @param errorCode the error code
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


}
