package com.project.tutorplatform.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.project.tutorplatform.entity.Notification;
import com.project.tutorplatform.repository.NotificationRepository;
import com.project.tutorplatform.service.PushNotificationService;

@Component
public class NotificationScheduler {

    private final NotificationRepository notificationRepository;
    private final PushNotificationService pushService;

    public NotificationScheduler(NotificationRepository notificationRepository,
                                 PushNotificationService pushService) {
        this.notificationRepository = notificationRepository;
        this.pushService = pushService;
    }

    @Scheduled(fixedRate = 60000)
    public void sendPendingNotifications() {

        List<Notification> notifications =
                notificationRepository.findBySentAtIsNull();

        for (Notification n : notifications) {
            pushService.sendNotification(
                    n.getUser(),
                    n.getTitle(),
                    n.getMessage()
            );
            n.setSentAt(LocalDateTime.now());
            notificationRepository.save(n);
        }
    }
}