package com.project.tutorplatform.entity;
//package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tracking", indexes = {
        @Index(name = "idx_booking", columnList = "booking_id"),
        @Index(name = "idx_tutor", columnList = "tutor_id"),
        @Index(name = "idx_recorded_at", columnList = "recordedAt")
})
public class Tracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 📘 Related Booking
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    // 👨‍🏫 Tutor being tracked
    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    // 🌍 Coordinates
    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    // 🚀 Movement data
    @Column
    private Double speed;     // km/h

    @Column
    private Double heading;   // 0–360 degrees

    @Column
    private Double accuracy;  // meters

    // 🕒 GPS timestamp (from device)
    @Column(nullable = false)
    private LocalDateTime recordedAt;

    // ===== Constructors =====
    public Tracking() {}

    // ===== Getters & Setters =====
    public Long getId() { return id; }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }

    public Tutor getTutor() { return tutor; }
    public void setTutor(Tutor tutor) { this.tutor = tutor; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getSpeed() { return speed; }
    public void setSpeed(Double speed) { this.speed = speed; }

    public Double getHeading() { return heading; }
    public void setHeading(Double heading) { this.heading = heading; }

    public Double getAccuracy() { return accuracy; }
    public void setAccuracy(Double accuracy) { this.accuracy = accuracy; }

    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }
}