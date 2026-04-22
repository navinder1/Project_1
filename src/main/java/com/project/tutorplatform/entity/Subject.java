package com.project.tutorplatform.entity;

import com.project.tutorplatform.dto.enums.SubjectCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Subject name (example: Mathematics, Physics)
    @Column(nullable = false, unique = true)
    private String name;

    // Subject category enum
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubjectCategory category;

    // Subject description
    @Column(length = 500)
    private String description;

    // Icon URL (for UI display)
    @Column(length = 500)
    private String iconUrl;

    // Active status
    @Column(nullable = false)
    private boolean isActive = true;

    // Default Constructor
    public Subject() {
    }

    // Parameterized Constructor
    public Subject(String name,
                   SubjectCategory category,
                   String description,
                   String iconUrl,
                   boolean isActive) {

        this.name = name;
        this.category = category;
        this.description = description;
        this.iconUrl = iconUrl;
        this.isActive = isActive;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubjectCategory getCategory() {
        return category;
    }

    public void setCategory(SubjectCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}