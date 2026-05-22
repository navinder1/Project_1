package com.project.tutorplatform.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

import org.jspecify.annotations.Nullable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.tutorplatform.dto.request.LoginRequest;
import com.project.tutorplatform.dto.request.RegisterRequest;
import com.project.tutorplatform.dto.response.AuthResponse;
import com.project.tutorplatform.entity.Role;
import com.project.tutorplatform.repository.RoleRepository;
import com.project.tutorplatform.repository.UserRepository;
import com.project.tutorplatform.security.JwtTokenProvider;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    public AuthService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider,
                       RedisTemplate<String, String> redisTemplate) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
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

        com.project.tutorplatform.entity.User appUser =
                new com.project.tutorplatform.entity.User();

        appUser.setUsername(req.getUsername());
        appUser.setEmail(req.getEmail());
        appUser.setPhoneNumber(req.getPhoneNumber());
        appUser.setFullName(req.getFullName());
        appUser.setPassword(passwordEncoder.encode(req.getPassword()));
        appUser.setIsActive(true);
        appUser.setIsVerified(false);
        appUser.setRoles(Collections.singleton(role));

        userRepository.save(appUser);

        User securityUser = new User(
                appUser.getEmail(),
                appUser.getPassword(),
                Collections.emptyList()
        );

        String token =
                jwtTokenProvider.generateToken(securityUser);

        String refreshToken =
                jwtTokenProvider.generateRefreshToken(securityUser);

        return new AuthResponse(token, refreshToken);
    }

    public AuthResponse login(LoginRequest req) {

        com.project.tutorplatform.entity.User appUser =
                userRepository.findByEmail(req.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                req.getPassword(),
                appUser.getPassword())) {

            throw new RuntimeException("Invalid credentials");
        }

        if (!appUser.getIsActive()) {
            throw new RuntimeException("User inactive");
        }

        appUser.setLastLoginAt(LocalDateTime.now());

        userRepository.save(appUser);

        User securityUser = new User(
                appUser.getEmail(),
                appUser.getPassword(),
                Collections.emptyList()
        );

        String token =
                jwtTokenProvider.generateToken(securityUser);

        String refreshToken =
                jwtTokenProvider.generateRefreshToken(securityUser);

        return new AuthResponse(token, refreshToken);
    }

    public void sendOtp(String identifier) {

        String otp =
                String.valueOf(100000 + new Random().nextInt(900000));

        redisTemplate.opsForValue().set(
                "OTP:" + identifier,
                otp,
                Duration.ofMinutes(10)
        );
    }

    public boolean verifyOtp(String identifier, String otp) {

        String stored =
                redisTemplate.opsForValue().get("OTP:" + identifier);

        if (stored != null && stored.equals(otp)) {

            redisTemplate.delete("OTP:" + identifier);

            return true;
        }

        return false;
    }

    public void forgotPassword(String email) {

        userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(
                "RESET:" + token,
                email,
                Duration.ofMinutes(15)
        );
    }

    public void resetPassword(String token, String newPassword) {

        String email =
                redisTemplate.opsForValue().get("RESET:" + token);

        if (email == null) {
            throw new RuntimeException("Invalid token");
        }

        com.project.tutorplatform.entity.User user =
                userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        redisTemplate.delete("RESET:" + token);
    }

    public void logout(String token) {

        redisTemplate.opsForValue().set(
                "BLACKLIST:" + token,
                "true",
                Duration.ofDays(1)
        );
    }

	public @Nullable Object refreshToken(String refreshToken) {
		return null;
	}
}