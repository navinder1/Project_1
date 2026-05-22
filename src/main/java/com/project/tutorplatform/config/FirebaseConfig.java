package com.project.tutorplatform.config;

import java.io.InputStream;

import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {

        try {

            InputStream serviceAccount =
                    getClass().getClassLoader()
                    .getResourceAsStream("firebase-service-account.json");

            if (serviceAccount == null) {
                throw new RuntimeException("Firebase JSON file not found");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            System.out.println("Firebase Initialized Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}