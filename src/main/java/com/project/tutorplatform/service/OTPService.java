package com.project.tutorplatform.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class OTPService {

    private final Map<String, String> otpStore = new HashMap<>();

    public String generateOTP(String key) {
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        otpStore.put(key, otp);
        return otp;
    }

    public boolean verifyOTP(String key, String otp) {
        return otp.equals(otpStore.get(key));
    }
}