package com.project.tutorplatform.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.tutorplatform.entity.Booking;
import com.project.tutorplatform.entity.Tutor;
import com.project.tutorplatform.entity.User;
import com.project.tutorplatform.exception.ResourceNotFoundException;
import com.project.tutorplatform.repository.BookingRepository;
import com.project.tutorplatform.repository.TutorRepository;
import com.project.tutorplatform.repository.UserRepository;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final TutorRepository tutorRepository;
    private final BookingRepository bookingRepository;

    public AdminService(UserRepository userRepository,
                        TutorRepository tutorRepository,
                        BookingRepository bookingRepository) {

        this.userRepository = userRepository;
        this.tutorRepository = tutorRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Tutor> getAllTutors() {
        return tutorRepository.findAll();
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Tutor approveTutor(Long tutorId) {

        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tutor not found"));

        tutor.setIsApproved(true);

        return tutorRepository.save(tutor);
    }

    public User blockUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        user.setIsActive(false);

        return userRepository.save(user);
    }

    public User unblockUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        user.setIsActive(true);

        return userRepository.save(user);
    }

    public void deleteBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        bookingRepository.delete(booking);
    }
}