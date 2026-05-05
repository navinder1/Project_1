package com.project.tutorplatform.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.tutorplatform.entity.Notification;
import com.project.tutorplatform.entity.User;
import com.project.tutorplatform.enums.NotificationChannel;
import com.project.tutorplatform.enums.NotificationType;
import com.project.tutorplatform.exception.ResourceNotFoundException;
import com.project.tutorplatform.repository.NotificationRepository;
import com.project.tutorplatform.repository.UserRepository;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    private final EmailService emailService;
    private final SMSService smsService;
    private final PushNotificationService pushService;

    public NotificationService(NotificationRepository notificationRepository,
                               UserRepository userRepository,
                               EmailService emailService,
                               SMSService smsService,
                               PushNotificationService pushService) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.smsService = smsService;
        this.pushService = pushService;
    }

    public Notification sendNotification(Long userId,
                                         String title,
                                         String message,
                                         NotificationType type,
                                         NotificationChannel channel,
                                         Long referenceId,
                                         String referenceType) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType(type);
        notification.setChannel(channel);
        notification.setIsRead(false);
        notification.setReferenceId(referenceId);
        notification.setReferenceType(referenceType);
        notification.setSentAt(LocalDateTime.now());

        notificationRepository.save(notification);

        // 🔥 Send via channel
        switch (channel) {
            case EMAIL -> emailService.sendEmail(user.getEmail(), title, message);
            case SMS -> smsService.sendSMS(user.getPhoneNumber(), message);
            case PUSH -> pushService.sendPush(user.getFcmToken(), title, message);
            case IN_APP -> {
                // already stored, no external send
            }
        }

        return notification;
    }

    public Page<Notification> getUserNotifications(Long userId, Pageable pageable) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    public long getUnreadCount(Long userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }

    public void markAsRead(Long notificationId) {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));

        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    public void markAllAsRead(Long userId) {
        notificationRepository.markAllReadByUserId(userId);
    }
}