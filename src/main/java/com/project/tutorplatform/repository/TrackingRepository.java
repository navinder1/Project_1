package com.project.tutorplatform.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tutorplatform.entity.Tracking;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {

    Optional<Tracking> findFirstByBookingIdOrderByRecordedAtDesc(Long bookingId);

    List<Tracking> findByBookingIdOrderByRecordedAtAsc(Long bookingId);

    void deleteByRecordedAtBefore(LocalDateTime threshold);
}