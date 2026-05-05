package com.project.tutorplatform.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.tutorplatform.entity.Subject;
import com.project.tutorplatform.exception.ResourceNotFoundException;
import com.project.tutorplatform.repository.SubjectRepository;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Subject create(Subject subject) {
        subject.setActive(true);
        return subjectRepository.save(subject);
    }

    public Subject getById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
    }

    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    public List<Subject> getActiveSubjects() {
        return subjectRepository.findByIsActiveTrue();
    }

    public Subject update(Long id, Subject updated) {

        Subject subject = getById(id);

        subject.setName(updated.getName());
        subject.setCategory(updated.getCategory());
        subject.setDescription(updated.getDescription());
        subject.setIconUrl(updated.getIconUrl());

        return subjectRepository.save(subject);
    }

    public void deactivate(Long id) {

        Subject subject = getById(id);
        subject.setActive(false);

        subjectRepository.save(subject);
    }

    public void activate(Long id) {

        Subject subject = getById(id);
        subject.setActive(true);

        subjectRepository.save(subject);
    }

    public void delete(Long id) {

        Subject subject = getById(id);
        subjectRepository.delete(subject);
    }
}