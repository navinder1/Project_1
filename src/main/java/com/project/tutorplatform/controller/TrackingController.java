package com.project.tutorplatform.controller;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.tutorplatform.dto.request.TrackingRequest;
import com.project.tutorplatform.entity.Tracking;
import com.project.tutorplatform.service.TrackingService;

@RestController
@RequestMapping("/api/tracking")
public class TrackingController {

    private final TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }
    @PostMapping
    public ResponseEntity<@Nullable Object> saveTracking(
            @RequestBody TrackingRequest request) {

        return ResponseEntity.ok(
                trackingService.saveLocation(request)
        );
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<@Nullable Object> getTracking(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                trackingService.getBookingTracking(bookingId)
        );
    }
}