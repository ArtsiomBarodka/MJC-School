package com.epam.esm.rest.handler;

import com.epam.esm.model.exception.security.InvalidJwtAuthenticationException;
import com.epam.esm.model.exception.service.BadParametersException;
import com.epam.esm.model.exception.service.ResourceAlreadyExistException;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String EXCEPTION_ERROR_CODE = "00";
    private static final String EXCEPTION_AUTHENTICATION_ERROR_CODE = "42";
    private static final String EXCEPTION_ACCESS_DENIED_ERROR_CODE = "43";

    private static final String RESOURCE_NOT_FOUND_MESSAGE_EXCEPTION = "Exception.resourceNotFound";
    private static final String RESOURCE_ALREADY_EXIST_MESSAGE_EXCEPTION = "Exception.resourceAlreadyExist";
    private static final String BAD_PARAMETERS_MESSAGE_EXCEPTION = "Exception.badParameters";
    private static final String MESSAGE_EXCEPTION = "Exception.service";
    private static final String AUTHENTICATION_EXCEPTION = "Exception.service";
    private static final String ACCESS_DENIED_EXCEPTION = "Exception.service";
    private static final String INVALID_JWT_AUTHENTICATION_EXCEPTION = "Exception.service";

    private final MessageSource messageSource;

    @Autowired
    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, Locale locale) {
        String message = messageSource.getMessage(RESOURCE_NOT_FOUND_MESSAGE_EXCEPTION, null, locale);
        String errorCode = HttpStatus.NOT_FOUND.value() + ex.getErrorCode();
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND, message, errorCode);
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(value = ResourceAlreadyExistException.class)
    protected ResponseEntity<Object> handleResourceAlreadyExistException(ResourceAlreadyExistException ex, Locale locale) {
        String message = messageSource.getMessage(RESOURCE_ALREADY_EXIST_MESSAGE_EXCEPTION, null, locale);
        String errorCode = HttpStatus.CONFLICT.value() + ex.getErrorCode();
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.CONFLICT, message, errorCode);
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(value = BadParametersException.class)
    protected ResponseEntity<Object> handleBadParametersException(BadParametersException ex, Locale locale) {
        String message = messageSource.getMessage(BAD_PARAMETERS_MESSAGE_EXCEPTION, null, locale);
        String errorCode = HttpStatus.BAD_REQUEST.value() + ex.getErrorCode();
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, message, errorCode);
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleALLException(Locale locale) {
        String message = messageSource.getMessage(MESSAGE_EXCEPTION, null, locale);
        String errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value() + EXCEPTION_ERROR_CODE;
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, errorCode);
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorCode = HttpStatus.BAD_REQUEST.value() + EXCEPTION_ERROR_CODE;

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errorCode);
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthenticationException(Locale locale) {
        String message = messageSource.getMessage(AUTHENTICATION_EXCEPTION, null, locale);
        String errorCode = HttpStatus.UNAUTHORIZED.value() + EXCEPTION_AUTHENTICATION_ERROR_CODE;
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, message, errorCode);
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(Locale locale) {
        String message = messageSource.getMessage(ACCESS_DENIED_EXCEPTION, null, locale);
        String errorCode = HttpStatus.FORBIDDEN.value() + EXCEPTION_ACCESS_DENIED_ERROR_CODE;
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.FORBIDDEN, message, errorCode);
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(value = InvalidJwtAuthenticationException.class)
    protected ResponseEntity<Object> handleInvalidJwtAuthenticationException(InvalidJwtAuthenticationException ex, Locale locale) {
        String message = messageSource.getMessage(INVALID_JWT_AUTHENTICATION_EXCEPTION, null, locale);
        String errorCode = HttpStatus.UNAUTHORIZED.value() + ex.getErrorCode();
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, message, errorCode);
        return buildResponseEntity(apiErrorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream().map(this::buildMessage)
                .collect(Collectors.toList()).toString();

        String errorCode = HttpStatus.BAD_REQUEST.value() + EXCEPTION_ERROR_CODE;

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, message, errorCode);
        return buildResponseEntity(apiErrorResponse);
    }


    private ResponseEntity<Object> buildResponseEntity(ApiErrorResponse apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatusCode());
    }

    private String buildMessage(FieldError fe) {
        StringBuilder errorCode = new StringBuilder("error.");
        errorCode.append(fe.getObjectName()).append(".");
        errorCode.append(fe.getField()).append(".");
        errorCode.append(Objects.requireNonNull(fe.getCode()).toLowerCase());

        try {
            return messageSource.getMessage(errorCode.toString(), fe.getArguments(), LocaleContextHolder.getLocale());
        } catch (Exception ex) {
            return fe.getDefaultMessage();
        }
    }
}
