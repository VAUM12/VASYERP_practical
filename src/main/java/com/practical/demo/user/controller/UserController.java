package com.practical.demo.user.controller;

import com.practical.demo.user.dto.AuthRequest;
import com.practical.demo.user.dto.UserRegistrationRequest;
import com.practical.demo.user.model.UserInfo;
import com.practical.demo.user.service.UserInfoService;
import com.practical.demo.exception.ExceptionResponse;
import com.practical.demo.jwtconfig.JwtService;
import com.practical.demo.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication API", description = "Handles user registration and login")
public class UserController {

    @Autowired
    private UserInfoService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Register a new user", description = "Creates a new user and hashes the password")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody UserRegistrationRequest user, HttpServletRequest request) {
        String result = userService.addUser(user);
        return ResponseEntity.ok(new ApiResponse<>("success", "User registered successfully", result, request.getRequestURI()));
    }

    @Operation(summary = "Login and generate JWT", description = "Authenticates user and returns JWT token")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginAndGenerateToken(@RequestBody AuthRequest authRequest, HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (AuthenticationException ex) {
            throw new ExceptionResponse("Invalid email or password.", HttpStatus.BAD_REQUEST);
        }

        UserInfo user = userService.getUserByEmail(authRequest.getUsername());
        String token = jwtService.generateToken(user.getEmail(), user.getId(), user.getRoles());
        return ResponseEntity.ok(new ApiResponse<>("success", "JWT generated successfully", token, request.getRequestURI()));

    }

    @Operation(summary = "Get user details", description = "Returns details of authenticated user")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserInfo>> getUserProfile(@RequestAttribute("id") Long userId, HttpServletRequest request) {
        UserInfo user = userService.getUserById(userId);
        return ResponseEntity.ok(new ApiResponse<>("success", "User fetched successfully", user, request.getRequestURI()));
    }
}
