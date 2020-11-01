package com.epam.esm.component;

import org.springframework.http.HttpStatus;

public class ApiErrorResponse {
    private HttpStatus statusCode;
    private String message;
    private String errorCode;

    public ApiErrorResponse() {
    }

    public ApiErrorResponse(HttpStatus statusCode, String message, String errorCode) {
        this.statusCode = statusCode;
        this.message = message;
        this.errorCode = errorCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


}
