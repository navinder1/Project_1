package com.project.tutorplatform.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.project.tutorplatform.entity.Booking;
import com.project.tutorplatform.enums.BookingStatus;
import com.project.tutorplatform.repository.BookingRepository;
import com.project.tutorplatform.service.NotificationService;

@Component
public class ReminderScheduler {

    private final BookingRepository bookingRepository;
    private final NotificationService notificationService;

    public ReminderScheduler(BookingRepository bookingRepository,
                             NotificationService notificationService) {
        this.bookingRepository = bookingRepository;
        this.notificationService = notificationService;
    }

    @Scheduled(fixedRate = 300000)
    public void sendReminders() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextHour = now.plusHours(1);

        List<Booking> bookings =
                bookingRepository.findByStatusAndEndTimeBefore(
                        BookingStatus.CONFIRMED,
                        nextHour
                );

        for (Booking b : bookings) {
            notificationService.createNotification(
                    b.getStudent().getUser(),
                    "Session Reminder",
                    "Your class starts soon"
            );
        }
    }
}