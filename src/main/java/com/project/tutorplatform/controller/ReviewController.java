package com.project.tutorplatform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.tutorplatform.dto.request.ReviewRequest;
import com.project.tutorplatform.service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<String> createReview(
            @RequestBody ReviewRequest request) {
    	reviewService.createReview(request);

        return ResponseEntity.ok("Review Added Successfully");
    }
}
