package com.practical.demo.exception;

import org.springframework.http.HttpStatus;

public class ExceptionResponse extends RuntimeException{

    private String message;
    private HttpStatus httpStatus;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ExceptionResponse() {}

    public ExceptionResponse(String msg,HttpStatus httpStatus) {
        super(msg);
        this.message = msg;
        this.httpStatus=httpStatus;
    }

}
