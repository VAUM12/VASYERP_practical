package com.practical.demo.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Hidden
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExceptionResponse.class)
    public ResponseEntity<ErrorResponseWrapper> handleException(ExceptionResponse ex, WebRequest webRequest) {
        ErrorResponseWrapper errorResponseWrapper = new ErrorResponseWrapper();
        errorResponseWrapper.setMessage(ex.getMessage());
        errorResponseWrapper.setStatus(ex.getHttpStatus().value());
        errorResponseWrapper.setTimestamp(System.currentTimeMillis());
        errorResponseWrapper.setApiPath(webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponseWrapper, HttpStatus.BAD_REQUEST);
    }


}
