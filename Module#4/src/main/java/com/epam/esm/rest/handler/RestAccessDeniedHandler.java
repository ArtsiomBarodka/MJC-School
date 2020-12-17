package com.epam.esm.rest.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static com.epam.esm.rest.handler.RestResponseConstants.ACCESS_DENIED_EXCEPTION;
import static com.epam.esm.rest.handler.RestResponseConstants.EXCEPTION_ACCESS_DENIED_ERROR_CODE;

@Component
@AllArgsConstructor
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    private final MessageSource messageSource;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        String message = messageSource.getMessage(ACCESS_DENIED_EXCEPTION, null, httpServletRequest.getLocale());
        String errorCode = HttpStatus.FORBIDDEN.value() + EXCEPTION_ACCESS_DENIED_ERROR_CODE;
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.FORBIDDEN, message, errorCode);

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());

        OutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, response);
        out.flush();
    }
}
