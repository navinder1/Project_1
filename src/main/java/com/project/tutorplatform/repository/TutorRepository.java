package com.project.tutorplatform.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tutorplatform.entity.Tutor;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

    Page<Tutor> findByIsApprovedAndIsAvailable(boolean approved, boolean available, Pageable page);

    List<Tutor> findBySubjects_Id(Long subjectId);

    List<Tutor> findByRatingGreaterThanEqual(Double minRating);

    List<Tutor> findByHourlyRateBetween(BigDecimal min, BigDecimal max);
}