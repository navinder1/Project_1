package com.project.tutorplatform.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.tutorplatform.entity.Booking;
import com.project.tutorplatform.entity.Tutor;
import com.project.tutorplatform.entity.User;
import com.project.tutorplatform.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/tutors")
    public ResponseEntity<List<Tutor>> getTutors() {
        return ResponseEntity.ok(adminService.getAllTutors());
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookings() {
        return ResponseEntity.ok(adminService.getAllBookings());
    }

    @PutMapping("/approve-tutor/{tutorId}")
    public ResponseEntity<Tutor> approveTutor(
            @PathVariable Long tutorId) {

        return ResponseEntity.ok(
        		adminService.approveTutor(tutorId)
                );
            }

            @PutMapping("/block-user/{userId}")
            public ResponseEntity<User> blockUser(
                    @PathVariable Long userId) {

                return ResponseEntity.ok(
                        adminService.blockUser(userId)
                );
            }

            @PutMapping("/unblock-user/{userId}")
            public ResponseEntity<User> unblockUser(
                    @PathVariable Long userId) {

                return ResponseEntity.ok(
                        adminService.unblockUser(userId)
                );
            }
            @DeleteMapping("/booking/{bookingId}")
            public ResponseEntity<String> deleteBooking(
                    @PathVariable Long bookingId) {

                adminService.deleteBooking(bookingId);

                return ResponseEntity.ok("Booking Deleted Successfully");
            }
        }