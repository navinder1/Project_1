package com.project.tutorplatform.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.project.tutorplatform.dto.enums.TransactionType;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many transactions belong to one wallet
    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    // Amount credited or debited
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    // CREDIT or DEBIT
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    // Description of transaction
    @Column(length = 255)
    private String description;

    // Reference ID (Booking ID / Payment ID / Withdrawal ID)
    @Column(length = 100)
    private String referenceId;

    // Timestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Default Constructor
    public Transaction() {
    }

    // Parameterized Constructor
    public Transaction(Wallet wallet,
                       BigDecimal amount,
                       TransactionType type,
                       String description,
                       String referenceId) {

        this.wallet = wallet;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.referenceId = referenceId;
    }

    // Automatically set created time
    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}