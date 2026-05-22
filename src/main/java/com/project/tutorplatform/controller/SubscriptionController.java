package com.project.tutorplatform.controller;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.tutorplatform.entity.Subscription;
import com.project.tutorplatform.service.SubscriptionService;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/{tutorId}")
    public ResponseEntity<@Nullable Object> createSubscription(
            @PathVariable Long tutorId,
            @RequestParam String plan) {
    	return ResponseEntity.ok(
                subscriptionService.createSubscription(tutorId, plan)
        );
    }
}
 