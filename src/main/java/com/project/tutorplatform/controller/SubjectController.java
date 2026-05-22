package com.project.tutorplatform.controller;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.tutorplatform.entity.Subject;
import com.project.tutorplatform.service.SubjectService;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public ResponseEntity<@Nullable Object> getSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<@Nullable Object> getSubject(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.getSubjectById(id));
    }
}