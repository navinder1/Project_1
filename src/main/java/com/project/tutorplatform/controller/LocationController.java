package com.project.tutorplatform.controller;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.tutorplatform.dto.request.LocationRequest;
import com.project.tutorplatform.entity.Location;
import com.project.tutorplatform.service.LocationService;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }
    @PostMapping
    public ResponseEntity<@Nullable Object> saveLocation(
            @RequestBody LocationRequest request) {

        return ResponseEntity.ok(
                locationService.saveLocation(request)
        );
    }
}
    