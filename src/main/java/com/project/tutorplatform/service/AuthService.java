package com.project.tutorplatform.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

import jakarta.annotation.PostConstruct;

@Service
public class AuthService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.verify.service.sid}")
    private String serviceSid;

    // ✅ Initialize Twilio AFTER Spring injects values
    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    // ✅ SEND OTP
    public String sendOTP(String phone) {
        Verification verification = Verification.creator(
                serviceSid,
                phone,
                "sms"
        ).create();

        return "OTP SENT SUCCESSFULLY";
    }

    // ✅ VERIFY OTP
    public String verifyOTP(String phone, String otp) {

        VerificationCheck check = VerificationCheck.creator(serviceSid)
                .setTo(phone)
                .setCode(otp)   // ✅ IMPORTANT FIX
                .create();

        if ("approved".equals(check.getStatus())) {
            return "LOGIN SUCCESS";
        } else {
            return "INVALID OTP";
        }
    }
}