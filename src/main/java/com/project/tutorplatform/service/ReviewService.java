package com.project.tutorplatform.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.tutorplatform.entity.Booking;
import com.project.tutorplatform.entity.Review;
import com.project.tutorplatform.entity.Student;
import com.project.tutorplatform.entity.Tutor;
import com.project.tutorplatform.exception.BadRequestException;
import com.project.tutorplatform.exception.ResourceNotFoundException;
import com.project.tutorplatform.repository.BookingRepository;
import com.project.tutorplatform.repository.ReviewRepository;
import com.project.tutorplatform.repository.StudentRepository;
import com.project.tutorplatform.repository.TutorRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final StudentRepository studentRepository;
    private final TutorRepository tutorRepository;
    private final TutorService tutorService;

    public ReviewService(ReviewRepository reviewRepository,
                         BookingRepository bookingRepository,
                         StudentRepository studentRepository,
                         TutorRepository tutorRepository,
                         TutorService tutorService) {
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository;
        this.studentRepository = studentRepository;
        this.tutorRepository = tutorRepository;
        this.tutorService = tutorService;
    }

    public Review createReview(Long bookingId, Long studentId, Review review) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (reviewRepository.existsByBookingId(bookingId)) {
            throw new BadRequestException("Review already exists for this booking");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Tutor tutor = tutorRepository.findById(booking.getTutor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Tutor not found"));

        review.setBooking(booking);
        review.setStudent(student);
        review.setTutor(tutor);

        Review saved = reviewRepository.save(review);

        // 🔥 Update tutor rating
        tutorService.updateRating(tutor.getId(), review.getRating());

        return saved;
    }

    public Page<Review> getTutorReviews(Long tutorId, Pageable pageable) {
        return reviewRepository.findByTutorId(tutorId, pageable);
    }

    public Double getAverageRating(Long tutorId) {
        Double avg = reviewRepository.findAverageRatingByTutorId(tutorId);
        return avg != null ? avg : 0.0;
    }

    public Review getById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
    }

    public Review addTutorReply(Long reviewId, String reply) {

        Review review = getById(reviewId);
        review.setTutorReply(reply);

        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {

        Review review = getById(id);
        reviewRepository.delete(review);
    }
}