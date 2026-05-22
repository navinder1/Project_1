package com.project.tutorplatform.service;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.project.tutorplatform.entity.User;

@Service
public class PushNotificationService {

    public void sendPush(String token, String title, String body) {

        if (token == null || token.isBlank()) return;

        Message message = Message.builder()
                .setToken(token)
                .setNotification(
                        Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build()
                )
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (Exception e) {
            throw new RuntimeException("Push notification failed");
        }
    }

	public void sendNotification(User user, String title, String message) {
	}
}