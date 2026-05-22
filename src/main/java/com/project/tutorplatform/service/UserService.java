package com.project.tutorplatform.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.tutorplatform.entity.User;
import com.project.tutorplatform.exception.ResourceNotFoundException;
import com.project.tutorplatform.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User create(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        user.setIsActive(true);
        user.setIsVerified(false);

        return userRepository.save(user);
    }

    public User update(Long id, User updatedUser) {

        User user = getById(id);

        user.setFullName(updatedUser.getFullName());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setProfilePictureUrl(updatedUser.getProfilePictureUrl());

        return userRepository.save(user);
    }

    public void changePassword(Long userId, String newPassword) {

        User user = getById(userId);
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

    public void deactivate(Long userId) {

        User user = getById(userId);
        user.setIsActive(false);

        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

	public @Nullable Object getAllUsers() {
		return null;
	}

	public @Nullable Object getUserById(Long id) {
		return null;
	}
}