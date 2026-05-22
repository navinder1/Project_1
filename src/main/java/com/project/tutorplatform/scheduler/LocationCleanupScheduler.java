package com.project.tutorplatform.scheduler;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.project.tutorplatform.repository.TrackingRepository;

@Component
public class LocationCleanupScheduler {

    private final TrackingRepository trackingRepository;

    public LocationCleanupScheduler(TrackingRepository trackingRepository) {
        this.trackingRepository = trackingRepository;
    }

    @Scheduled(cron = "0 0 */6 * * ?")
    public void cleanupOldLocations() {

        LocalDateTime threshold = LocalDateTime.now().minusHours(24);

        trackingRepository.deleteByRecordedAtBefore(threshold);
    }
}