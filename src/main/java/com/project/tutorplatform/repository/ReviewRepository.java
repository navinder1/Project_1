package com.project.tutorplatform.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.tutorplatform.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByTutorId(Long tutorId, Pageable page);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.tutor.id = :tutorId")
    Double findAverageRatingByTutorId(@Param("tutorId") Long tutorId);

    boolean existsByBookingId(Long bookingId);
}