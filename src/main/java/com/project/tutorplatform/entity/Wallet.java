package com.project.tutorplatform.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One wallet belongs to one tutor
    @OneToOne
    @JoinColumn(name = "tutor_id", nullable = false, unique = true)
    private Tutor tutor;

    // Current balance
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    // Total earned amount
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalEarned = BigDecimal.ZERO;

    // Total withdrawn amount
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalWithdrawn = BigDecimal.ZERO;

    // Last update timestamp
    @Column(nullable = false)
    private LocalDateTime lastUpdatedAt;

    // Default Constructor
    public Wallet() {
    }

    // Parameterized Constructor
    public Wallet(Tutor tutor) {
        this.tutor = tutor;
        this.balance = BigDecimal.ZERO;
        this.totalEarned = BigDecimal.ZERO;
        this.totalWithdrawn = BigDecimal.ZERO;
        this.lastUpdatedAt = LocalDateTime.now();
    }

    // Automatically update timestamp before saving
    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        this.lastUpdatedAt = LocalDateTime.now();
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getTotalEarned() {
        return totalEarned;
    }

    public void setTotalEarned(BigDecimal totalEarned) {
        this.totalEarned = totalEarned;
    }

    public BigDecimal getTotalWithdrawn() {
        return totalWithdrawn;
    }

    public void setTotalWithdrawn(BigDecimal totalWithdrawn) {
        this.totalWithdrawn = totalWithdrawn;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
}