package com.project.tutorplatform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tutorplatform.entity.Subject;
import com.project.tutorplatform.enums.SubjectCategory;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Optional<Subject> findByNameIgnoreCase(String name);

    List<Subject> findByCategory(SubjectCategory category);

    List<Subject> findByIsActiveTrue();
}