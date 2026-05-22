package com.project.tutorplatform.controller;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.tutorplatform.dto.response.TutorResponse;
import com.project.tutorplatform.service.TutorService;

@RestController
@RequestMapping("/api/tutors")
public class TutorController {

    private final TutorService tutorService;

    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }
    @GetMapping
    public ResponseEntity<@Nullable Object> getAllTutors() {
        return ResponseEntity.ok(tutorService.getAllTutors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<@Nullable Object> getTutorById(@PathVariable Long id) {
        return ResponseEntity.ok(tutorService.getTutorById(id));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<@Nullable Object> getTutorsBySubject(@PathVariable Long subjectId) {
        return ResponseEntity.ok(tutorService.getTutorsBySubject(subjectId));
    }
}