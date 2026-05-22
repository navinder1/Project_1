package com.project.tutorplatform.controller;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.tutorplatform.entity.Notification;
import com.project.tutorplatform.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @GetMapping("/{userId}")
    public ResponseEntity<@Nullable Object> getNotifications(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationService.getUserNotifications(userId)
        );
    }
}