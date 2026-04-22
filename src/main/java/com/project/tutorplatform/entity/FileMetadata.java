package com.project.tutorplatform.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.project.tutorplatform.dto.enums.FilePurpose;

@Entity
@Table(name = "file_metadata")
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many files can belong to one tutor
    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    // Optional student reference (nullable)
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = true)
    private Student student;

    // Stored file name (system generated)
    @Column(nullable = false)
    private String fileName;

    // Original uploaded file name
    @Column(nullable = false)
    private String originalName;

    // File URL or path
    @Column(nullable = false, length = 500)
    private String fileUrl;

    // File MIME type (example: image/png, application/pdf)
    @Column(nullable = false)
    private String fileType;

    // File size in bytes
    @Column(nullable = false)
    private Long sizeBytes;

    // File purpose category
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FilePurpose purpose;

    // Upload timestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    // Default Constructor
    public FileMetadata() {
    }

    // Parameterized Constructor
    public FileMetadata(Tutor tutor,
                        Student student,
                        String fileName,
                        String originalName,
                        String fileUrl,
                        String fileType,
                        Long sizeBytes,
                        FilePurpose purpose) {

        this.tutor = tutor;
        this.student = student;
        this.fileName = fileName;
        this.originalName = originalName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.sizeBytes = sizeBytes;
        this.purpose = purpose;
    }

    // Automatically set upload time
    @PrePersist
    public void setUploadedAt() {
        this.uploadedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(Long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public FilePurpose getPurpose() {
        return purpose;
    }

    public void setPurpose(FilePurpose purpose) {
        this.purpose = purpose;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }
}