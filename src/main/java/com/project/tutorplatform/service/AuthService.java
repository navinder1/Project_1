package com.project.tutorplatform.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.tutorplatform.dto.request.LoginRequest;
import com.project.tutorplatform.dto.request.RegisterRequest;
import com.project.tutorplatform.dto.response.AuthResponse;
import com.project.tutorplatform.entity.Role;
import com.project.tutorplatform.entity.User;
import com.project.tutorplatform.repository.RoleRepository;
import com.project.tutorplatform.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RedisTemplate<String, String> redisTemplate;

    public AuthService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       RedisTemplate<String, String> redisTemplate) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.redisTemplate = redisTemplate;
    }

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByPhoneNumber(req.getPhoneNumber())) {
            throw new RuntimeException("Phone already exists");
        }

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPhoneNumber(req.getPhoneNumber());
        user.setFullName(req.getFullName());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setIsActive(true);
        user.setIsVerified(false);
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponse(token, refreshToken);
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        if (!user.getIsActive()) {
            throw new RuntimeException("User inactive");
        }

        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponse(token, refreshToken);
    }

    public AuthResponse refreshToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!jwtService.validateToken(refreshToken, user)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String newToken = jwtService.generateToken(user);
        String newRefresh = jwtService.generateRefreshToken(user);

        return new AuthResponse(newToken, newRefresh);
    }

    public void sendOtp(String identifier) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        redisTemplate.opsForValue().set("OTP:" + identifier, otp, Duration.ofMinutes(10));
    }

    public boolean verifyOtp(String identifier, String otp) {
        String key = "OTP:" + identifier;
        String stored = redisTemplate.opsForValue().get(key);

        if (stored != null && stored.equals(otp)) {
            redisTemplate.delete(key);

            userRepository.findByEmail(identifier).ifPresent(user -> {
                user.setIsVerified(true);
                userRepository.save(user);
            });

            return true;
        }
        return false;
    }

    public void forgotPassword(String email) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("RESET:" + token, email, Duration.ofMinutes(15));
    }

    public void resetPassword(String token, String newPassword) {
        String email = redisTemplate.opsForValue().get("RESET:" + token);

        if (email == null) {
            throw new RuntimeException("Invalid or expired token");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        redisTemplate.delete("RESET:" + token);
    }

    public void logout(String token) {
        String jti = jwtService.extractJti(token);
        long expiry = jwtService.getRemainingValidity(token);

        redisTemplate.opsForValue()
                .set("BLACKLIST:" + jti, "true", Duration.ofMillis(expiry));
    }
}