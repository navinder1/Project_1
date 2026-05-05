package com.project.tutorplatform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tutorplatform.entity.Subscription;
import com.project.tutorplatform.entity.Tutor;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByTutorId(Long tutorId);

    boolean existsByTutorId(Long tutorId);

	Optional<Tutor> findByTutorIdAndIsActiveTrue(Long tutorId);

	void deactivateAllByTutorId(Long tutorId);
}