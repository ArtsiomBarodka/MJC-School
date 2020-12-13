package com.epam.esm.rest.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApiErrorResponse {
    private HttpStatus statusCode;
    private String message;
    private String errorCode;
}
