package com.project.tutorplatform.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.tutorplatform.dto.request.BookingRequest;
import com.project.tutorplatform.dto.response.BookingResponse;
import com.project.tutorplatform.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/{studentId}")
    public ResponseEntity<BookingResponse> createBooking(
            @PathVariable Long studentId,
            @RequestBody BookingRequest request) {

        return ResponseEntity.ok(
                bookingService.createBooking(studentId, request)
        );
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBooking(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                bookingService.getBooking(bookingId)
        );
    }
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<BookingResponse>> getStudentBookings(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                bookingService.getStudentBookings(studentId)
        );
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<BookingResponse>> getTutorBookings(
            @PathVariable Long tutorId) {

        return ResponseEntity.ok(
                bookingService.getTutorBookings(tutorId)
        );
    }
    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancelBooking(
            @PathVariable Long bookingId,
            @RequestParam String reason) {

        bookingService.cancelBooking(bookingId, reason);

        return ResponseEntity.ok("Booking Cancelled Successfully");
    }

    @PutMapping("/complete/{bookingId}")
    public ResponseEntity<String> completeBooking(
            @PathVariable Long bookingId) {

        bookingService.completeBooking(bookingId);

        return ResponseEntity.ok("Booking Completed Successfully");
    }
}