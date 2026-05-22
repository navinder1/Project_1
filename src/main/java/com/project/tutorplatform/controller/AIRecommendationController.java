package com.project.tutorplatform.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.tutorplatform.entity.Tutor;
import com.project.tutorplatform.service.AIRecommendationService;

@RestController
@RequestMapping("/api/recommendations")
public class AIRecommendationController {

    private final AIRecommendationService aiRecommendationService;

    public AIRecommendationController(
            AIRecommendationService aiRecommendationService) {

        this.aiRecommendationService = aiRecommendationService;
    }

    @GetMapping
    public ResponseEntity<List<Tutor>> recommendTutors(
            @RequestParam Long subjectId,
            @RequestParam Double rating,
            @RequestParam BigDecimal maxPrice) {

        return ResponseEntity.ok(
                aiRecommendationService.recommendTutors(
                        subjectId,
                        rating,
                        maxPrice
                )
        );
    }
}