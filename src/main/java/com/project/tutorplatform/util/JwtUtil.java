package com.project.tutorplatform.util;

import java.util.Base64;

public class JwtUtil {

    public static String extractPayload(String token) {
        String[] parts = token.split("\\.");
        return new String(Base64.getDecoder().decode(parts[1]));
    }
}