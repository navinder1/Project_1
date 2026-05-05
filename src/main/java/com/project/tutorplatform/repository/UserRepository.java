package com.project.tutorplatform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tutorplatform.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

//    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

	boolean existsByPhoneNumber(String phoneNumber);
}