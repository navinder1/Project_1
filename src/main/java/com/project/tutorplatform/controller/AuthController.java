package com.project.tutorplatform.controller;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.tutorplatform.dto.request.LoginRequest;
import com.project.tutorplatform.dto.request.RegisterRequest;
import com.project.tutorplatform.dto.response.AuthResponse;
import com.project.tutorplatform.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(
                authService.register(request)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<@Nullable Object> refreshToken(
            @RequestParam String refreshToken) {

        return ResponseEntity.ok(
                authService.refreshToken(refreshToken)
        );
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(
            @RequestParam String identifier) {

        authService.sendOtp(identifier);

        return ResponseEntity.ok("OTP Sent Successfully");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Boolean> verifyOtp(
            @RequestParam String identifier,
            @RequestParam String otp) {

        return ResponseEntity.ok(
                authService.verifyOtp(identifier, otp)
        );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestParam String email) {

        authService.forgotPassword(email);

        return ResponseEntity.ok(
                "Reset Password Link Sent"
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword) {

        authService.resetPassword(token, newPassword);

        return ResponseEntity.ok(
                "Password Reset Successful"
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");

        authService.logout(token);

        return ResponseEntity.ok("Logged out successfully");
    }
}