package com.project.tutorplatform.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.tutorplatform.entity.Booking;
import com.project.tutorplatform.entity.Tracking;
import com.project.tutorplatform.entity.Tutor;
import com.project.tutorplatform.exception.ResourceNotFoundException;
import com.project.tutorplatform.repository.BookingRepository;
import com.project.tutorplatform.repository.TrackingRepository;
import com.project.tutorplatform.repository.TutorRepository;

@Service
public class TrackingService {

    private final TrackingRepository trackingRepository;
    private final BookingRepository bookingRepository;
    private final TutorRepository tutorRepository;

    public TrackingService(TrackingRepository trackingRepository,
                           BookingRepository bookingRepository,
                           TutorRepository tutorRepository) {
        this.trackingRepository = trackingRepository;
        this.bookingRepository = bookingRepository;
        this.tutorRepository = tutorRepository;
    }

    public Tracking saveLocation(Long bookingId,
                                 Long tutorId,
                                 double lat,
                                 double lon,
                                 Double speed,
                                 Double heading,
                                 Double accuracy) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor not found"));

        Tracking tracking = new Tracking();
        tracking.setBooking(booking);
        tracking.setTutor(tutor);
        tracking.setLatitude(lat);
        tracking.setLongitude(lon);
        tracking.setSpeed(speed);
        tracking.setHeading(heading);
        tracking.setAccuracy(accuracy);
        tracking.setRecordedAt(LocalDateTime.now());

        return trackingRepository.save(tracking);
    }

    public Tracking getLatestLocation(Long bookingId) {

        return trackingRepository
                .findFirstByBookingIdOrderByRecordedAtDesc(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Tracking not found"));
    }

    public List<Tracking> getRoute(Long bookingId) {
        return trackingRepository.findByBookingIdOrderByRecordedAtAsc(bookingId);
    }

    public void deleteOldData(int minutes) {

        LocalDateTime threshold = LocalDateTime.now().minusMinutes(minutes);

        trackingRepository.deleteByRecordedAtBefore(threshold);
    }
}