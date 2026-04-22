package com.project.tutorplatform.entity;

//package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;

import com.nimbusds.oauth2.sdk.id.Subject;
import com.project.tutorplatform.dto.enums.BookingStatus;
import com.project.tutorplatform.dto.enums.TeachingMode;
import com.twilio.rest.api.v2010.account.call.Payment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔢 Booking Number
    @Column(unique = true, length = 20)
    private String bookingNumber;

    // 👨‍🎓 Student
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // 👨‍🏫 Tutor
    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    // 📚 Subject
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    // 📅 Date & Time
    @Column(nullable = false)
    private LocalDate bookingDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    // ⏱ Duration (computed)
    @Column
    private Integer durationMinutes;

    // 🎓 Mode
    @Enumerated(EnumType.STRING)
    private TeachingMode teachingMode;

    // 🌐 Online / Offline details
    @Column(length = 500)
    private String meetingLink;

    @Column(length = 500)
    private String address;

    // 📌 Status
    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.PENDING;

    // 💰 Pricing
    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal platformFee;

    // 📝 Notes
    @Column(length = 1000)
    private String notes;

    @Column(length = 500)
    private String cancellationReason;

    // 💳 Payment
    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;

    // ⭐ Review
    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Review review;

    // 🕒 Created Time
    @CreationTimestamp
    private LocalDateTime createdAt;

    // ===== Lifecycle Hooks =====
    @PrePersist
    @PreUpdate
    private void calculateFields() {
        // ⏱ Calculate duration
        if (startTime != null && endTime != null) {
            this.durationMinutes = (int) Duration.between(startTime, endTime).toMinutes();
        }

        // 💰 Calculate total amount
        if (tutor != null && tutor.getHourlyRate() != null && durationMinutes != null) {
            BigDecimal hours = BigDecimal.valueOf(durationMinutes).divide(BigDecimal.valueOf(60));
            this.totalAmount = tutor.getHourlyRate().multiply(hours);

            // Example: 10% platform fee
            this.platformFee = totalAmount.multiply(BigDecimal.valueOf(0.10));
        }
    }

    // ===== Constructors =====
    public Booking() {}

    // ===== Getters & Setters =====
    public Long getId() { return id; }

    public String getBookingNumber() { return bookingNumber; }
    public void setBookingNumber(String bookingNumber) { this.bookingNumber = bookingNumber; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Tutor getTutor() { return tutor; }
    public void setTutor(Tutor tutor) { this.tutor = tutor; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public LocalDate getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public Integer getDurationMinutes() { return durationMinutes; }

    public TeachingMode getTeachingMode() { return teachingMode; }
    public void setTeachingMode(TeachingMode teachingMode) { this.teachingMode = teachingMode; }

    public String getMeetingLink() { return meetingLink; }
    public void setMeetingLink(String meetingLink) { this.meetingLink = meetingLink; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    public BigDecimal getTotalAmount() { return totalAmount; }

    public BigDecimal getPlatformFee() { return platformFee; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getCancellationReason() { return cancellationReason; }
    public void setCancellationReason(String cancellationReason) { this.cancellationReason = cancellationReason; }

    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }

    public Review getReview() { return review; }
    public void setReview(Review review) { this.review = review; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}