//package com.payment.gateway.exception;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import repeat.com.payment.gateway.DateUtils;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//import java.io.IOException;
//import java.util.Date;
//
//import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
//
//@ControllerAdvice
//@RequiredArgsConstructor
//public class CommonExceptionHandler {
//
//    private final ObjectMapper objectMapper;
//
//    @ExceptionHandler(AuthenticationException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public void handleAuthenticationError(AuthenticationException e, HttpServletResponse response) throws IOException {
//        handleGenericError("Authentication error: " + e.getLocalizedMessage(), response);
//    }
//
//    @ExceptionHandler(AuthorizationException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public void handleAuthorizationError(AuthorizationException e, HttpServletResponse response) throws IOException {
//        handleGenericError("Authorization error: " + e.getLocalizedMessage(), response);
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public void handleGenericError(Exception e, HttpServletResponse response) throws IOException {
//        handleGenericError("An error occurred: " + e.getLocalizedMessage(), response);
//    }
//
//    private void handleGenericError(String errorMessage, HttpServletResponse response) throws IOException {
//        ErrorPOJA errorPOJO = new ErrorPOJA();
//        errorPOJO.setError_description(errorMessage);
//        errorPOJO.setUser_description("An error occurred");
//        response.setStatus(response.getStatus());
//        errorPOJO.setCode(String.valueOf(response.getStatus()));
//        errorPOJO.setDate(DateUtils.sdfErrorPOJO.format(new Date()));
//        response.setContentType(APPLICATION_JSON_VALUE);
//        objectMapper.writeValue(response.getOutputStream(), errorPOJO);
//    }
//}


package com.payment.gateway.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.gateway.repeat.DateUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.Date;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ControllerAdvice
@RequiredArgsConstructor
public class CommonExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleAuthenticationError(AuthenticationException e, HttpServletResponse response) throws IOException {
        handleGenericError("Authentication error: " + e.getLocalizedMessage(), response, HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleAuthorizationError(AuthorizationException e, HttpServletResponse response) throws IOException {
        handleGenericError("Authorization error: " + e.getLocalizedMessage(), response, HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleGenericError(Exception e, HttpServletResponse response) throws IOException {
        handleGenericError("An error occurred: " + e.getLocalizedMessage(), response, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    private void handleGenericError(String errorMessage, HttpServletResponse response, int statusCode) throws IOException {
        ErrorPOJA errorPOJO = ErrorPOJA.builder()
                .error_description(errorMessage)
                .user_description("An error occurred")
                .code(String.valueOf(statusCode))
                .date(DateUtils.sdfErrorPOJO.format(new Date()))
                .request(null) // Assuming request is null in this context
                .build();
        response.setStatus(statusCode);
        response.setContentType(APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), errorPOJO);
    }
}
