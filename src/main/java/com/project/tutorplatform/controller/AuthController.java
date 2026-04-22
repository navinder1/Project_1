package com.project.tutorplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.tutorplatform.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")

@RequiredArgsConstructor
public class AuthController {
	@Autowired
    private AuthService authService;

    // SEND OTP
    @PostMapping("/send-otp")
    public String sendOTP(@RequestParam String phone) {
        return authService.sendOTP(phone);
    }

    // VERIFY OTP
    @PostMapping("/verify-otp")
    public String verifyOTP(@RequestParam String phone,
                            @RequestParam String otp) {
        return authService.verifyOTP(phone, otp);
    }
}