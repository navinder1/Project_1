package com.project.tutorplatform.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.project.tutorplatform.dto.request.BookingRequest;
import com.project.tutorplatform.dto.response.BookingResponse;
import com.project.tutorplatform.entity.Booking;
import com.project.tutorplatform.entity.Student;
import com.project.tutorplatform.entity.Subject;
import com.project.tutorplatform.entity.Tutor;
import com.project.tutorplatform.enums.BookingStatus;
import com.project.tutorplatform.repository.BookingRepository;
import com.project.tutorplatform.repository.StudentRepository;
import com.project.tutorplatform.repository.SubjectRepository;
import com.project.tutorplatform.repository.TutorRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final StudentRepository studentRepository;
    private final TutorRepository tutorRepository;
    private final SubjectRepository subjectRepository;

    public BookingService(BookingRepository bookingRepository,
                          StudentRepository studentRepository,
                          TutorRepository tutorRepository,
                          SubjectRepository subjectRepository) {
        this.bookingRepository = bookingRepository;
        this.studentRepository = studentRepository;
        this.tutorRepository = tutorRepository;
        this.subjectRepository = subjectRepository;
    }

    public BookingResponse createBooking(Long studentId, BookingRequest req) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Tutor tutor = tutorRepository.findById(req.getTutorId())
                .orElseThrow(() -> new RuntimeException("Tutor not found"));

        if (!tutor.getIsApproved() || !tutor.getIsAvailable()) {
            throw new RuntimeException("Tutor not available");
        }

        Subject subject = subjectRepository.findById(req.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        List<Booking> existingBookings = bookingRepository
                .findByTutorIdAndBookingDateAndStatus(
                        tutor.getId(),
                        req.getBookingDate(),
                        BookingStatus.CONFIRMED
                );

        existingBookings.forEach(b -> {
            boolean overlap =
                    req.getStartTime().isBefore(b.getEndTime()) &&
                    req.getEndTime().isAfter(b.getStartTime());

            if (overlap) {
                throw new RuntimeException("Time slot not available");
            }
        });

        Booking booking = new Booking();
        booking.setBookingNumber("BK-" + UUID.randomUUID().toString().substring(0, 8));
        booking.setStudent(student);
        booking.setTutor(tutor);
        booking.setSubject(subject);
        booking.setBookingDate(req.getBookingDate());
        booking.setStartTime(req.getStartTime());
        booking.setEndTime(req.getEndTime());
        booking.setTeachingMode(req.getTeachingMode());
        booking.setAddress(req.getAddress());
        booking.setMeetingLink(req.getMeetingLink());
        booking.setStatus(BookingStatus.PENDING);
        booking.setNotes(req.getNotes());

        bookingRepository.save(booking);

        return mapToResponse(booking);
    }

    public BookingResponse getBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return mapToResponse(booking);
    }

    public List<BookingResponse> getStudentBookings(Long studentId) {
        return bookingRepository.findByStudentId(studentId, PageRequest.of(0, 10))
                .map(this::mapToResponse)
                .getContent();
    }

    public List<BookingResponse> getTutorBookings(Long tutorId) {
        return bookingRepository.findByTutorId(tutorId, PageRequest.of(0, 10))
                .map(this::mapToResponse)
                .getContent();
    }

    public void cancelBooking(Long bookingId, String reason) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setCancellationReason(reason);

        bookingRepository.save(booking);
    }

    public void completeBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(BookingStatus.COMPLETED);

        bookingRepository.save(booking);
    }

    private BookingResponse mapToResponse(Booking booking) {

        BookingResponse res = new BookingResponse();

        res.setId(booking.getId());
        res.setBookingNumber(booking.getBookingNumber());
        res.setStudentId(booking.getStudent().getId());
        res.setTutorId(booking.getTutor().getId());
        res.setSubjectId(booking.getSubject().getId());
        res.setBookingDate(booking.getBookingDate());
        res.setStartTime(booking.getStartTime());
        res.setEndTime(booking.getEndTime());
        res.setDurationMinutes(booking.getDurationMinutes());
        res.setTeachingMode(booking.getTeachingMode());
        res.setAddress(booking.getAddress());
        res.setMeetingLink(booking.getMeetingLink());
        res.setStatus(booking.getStatus());
        res.setTotalAmount(booking.getTotalAmount());
        res.setPlatformFee(booking.getPlatformFee());
        res.setNotes(booking.getNotes());
        res.setCreatedAt(booking.getCreatedAt());

        return res;
    }
}