package com.epam.esm.rest.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static com.epam.esm.rest.handler.RestResponseConstants.AUTHENTICATION_EXCEPTION;
import static com.epam.esm.rest.handler.RestResponseConstants.EXCEPTION_AUTHENTICATION_ERROR_CODE;

@Component
@AllArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final MessageSource messageSource;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String message = messageSource.getMessage(AUTHENTICATION_EXCEPTION, null, httpServletRequest.getLocale());
        String errorCode = HttpStatus.UNAUTHORIZED.value() + EXCEPTION_AUTHENTICATION_ERROR_CODE;
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, message, errorCode);

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        OutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, response);
        out.flush();
    }
}