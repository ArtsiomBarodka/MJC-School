package com.epam.esm.rest.handler;

/**
 * The type Rest response constants.
 */
final class RestResponseConstants {
    /**
     * The constant EXCEPTION_ERROR_CODE.
     */
    public static final String EXCEPTION_ERROR_CODE = "00";
    /**
     * The constant EXCEPTION_AUTHENTICATION_ERROR_CODE.
     */
    public static final String EXCEPTION_AUTHENTICATION_ERROR_CODE = "31";
    /**
     * The constant EXCEPTION_ACCESS_DENIED_ERROR_CODE.
     */
    public static final String EXCEPTION_ACCESS_DENIED_ERROR_CODE = "32";

    /**
     * The constant RESOURCE_NOT_FOUND_MESSAGE_EXCEPTION.
     */
    public static final String RESOURCE_NOT_FOUND_MESSAGE_EXCEPTION = "Exception.resourceNotFound";
    /**
     * The constant RESOURCE_ALREADY_EXIST_MESSAGE_EXCEPTION.
     */
    public static final String RESOURCE_ALREADY_EXIST_MESSAGE_EXCEPTION = "Exception.resourceAlreadyExist";
    /**
     * The constant BAD_PARAMETERS_MESSAGE_EXCEPTION.
     */
    public static final String BAD_PARAMETERS_MESSAGE_EXCEPTION = "Exception.badParameters";
    /**
     * The constant MESSAGE_EXCEPTION.
     */
    public static final String MESSAGE_EXCEPTION = "Exception.service";
    /**
     * The constant AUTHENTICATION_EXCEPTION.
     */
    public static final String AUTHENTICATION_EXCEPTION = "Exception.authentication";
    /**
     * The constant ACCESS_DENIED_EXCEPTION.
     */
    public static final String ACCESS_DENIED_EXCEPTION = "Exception.accessDenied";

    private RestResponseConstants() {
    }
}
