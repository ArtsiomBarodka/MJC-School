package com.epam.esm.rest.handler;

final class RestResponseConstants {
    public static final String EXCEPTION_ERROR_CODE = "00";
    public static final String EXCEPTION_AUTHENTICATION_ERROR_CODE = "31";
    public static final String EXCEPTION_ACCESS_DENIED_ERROR_CODE = "32";

    public static final String RESOURCE_NOT_FOUND_MESSAGE_EXCEPTION = "Exception.resourceNotFound";
    public static final String RESOURCE_ALREADY_EXIST_MESSAGE_EXCEPTION = "Exception.resourceAlreadyExist";
    public static final String BAD_PARAMETERS_MESSAGE_EXCEPTION = "Exception.badParameters";
    public static final String MESSAGE_EXCEPTION = "Exception.service";
    public static final String AUTHENTICATION_EXCEPTION = "Exception.authentication";
    public static final String ACCESS_DENIED_EXCEPTION = "Exception.accessDenied";

    private RestResponseConstants() {
    }
}
