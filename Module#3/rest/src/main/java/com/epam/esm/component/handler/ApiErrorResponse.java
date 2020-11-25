package com.epam.esm.component.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * The type Api error response.
 */
@Data
@AllArgsConstructor
public class ApiErrorResponse {
    private HttpStatus statusCode;
    private String message;
    private String errorCode;
}
