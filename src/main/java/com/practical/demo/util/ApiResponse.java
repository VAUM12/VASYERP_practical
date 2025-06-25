package com.practical.demo.util;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

public class ApiResponse <T>{
    private String status;
    private String message;
    private T data;
    private Date timestamp;
    private String path;

    public ApiResponse(String status, String message, T data, String path) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = new Date();
        this.path = path;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date  getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date  timestamp) {
        this.timestamp = timestamp;
    }
}
