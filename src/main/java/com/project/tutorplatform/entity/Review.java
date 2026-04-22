package com.project.tutorplatform.entity;



import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "reviews", indexes = {
        @Index(name = "idx_tutor", columnList = "tutor_id"),
        @Index(name = "idx_student", columnList = "student_id")
})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 📘 Related Booking (1 review per booking)
    @OneToOne
    @JoinColumn(name = "booking_id", unique = true, nullable = false)
    private Booking booking;

    // 👨‍🎓 Student (author)
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // 👨‍🏫 Tutor (reviewed)
    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    // ⭐ Rating
    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private Integer rating;

    // 📝 Comment
    @Column(length = 2000)
    private String comment;

    // 🕵️ Anonymous option
    @Column(nullable = false)
    private Boolean isAnonymous = false;

    // 💬 Tutor reply
    @Column(length = 1000)
    private String tutorReply;

    // 🕒 Created time
    @CreationTimestamp
    private LocalDateTime createdAt;

    // ===== Constructors =====
    public Review() {}

    // ===== Getters & Setters =====
    public Long getId() { return id; }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Tutor getTutor() { return tutor; }
    public void setTutor(Tutor tutor) { this.tutor = tutor; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Boolean getIsAnonymous() { return isAnonymous; }
    public void setIsAnonymous(Boolean isAnonymous) { this.isAnonymous = isAnonymous; }

    public String getTutorReply() { return tutorReply; }
    public void setTutorReply(String tutorReply) { this.tutorReply = tutorReply; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}