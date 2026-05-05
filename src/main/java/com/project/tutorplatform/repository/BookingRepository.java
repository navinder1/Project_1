package com.project.tutorplatform.repository;

//import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tutorplatform.entity.Booking;
import com.project.tutorplatform.enums.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByTutorIdAndBookingDateAndStatus(Long tutorId, LocalDate date, BookingStatus status);

    Page<Booking> findByStudentId(Long studentId, Pageable page);

    Page<Booking> findByTutorId(Long tutorId, Pageable page);

    List<Booking> findByStatusAndEndTimeBefore(BookingStatus status, LocalDateTime time);
}