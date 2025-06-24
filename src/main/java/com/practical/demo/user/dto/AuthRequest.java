package com.practical.demo.user.dto;

public class AuthRequest {
    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AuthRequest(String password, String username) {
        this.password = password;
        this.username = username;
    }
    public AuthRequest() {
    }
}
