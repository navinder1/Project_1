package com.project.tutorplatform.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.project.tutorplatform.dto.enums.SubscriptionPlan;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many subscriptions belong to one tutor
    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    // Subscription Plan Enum
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionPlan plan;

    // Start Date
    @Column(nullable = false)
    private LocalDate startDate;

    // End Date
    @Column(nullable = false)
    private LocalDate endDate;

    // Active Status
    @Column(nullable = false)
    private boolean isActive;

    // Subscription Amount
    @Column(nullable = false)
    private double amount;

    // Razorpay Subscription ID
    @Column(name = "razorpay_subscription_id", unique = true)
    private String razorpaySubscriptionId;

    // Default Constructor
    public Subscription() {
    }

    // Parameterized Constructor
    public Subscription(Tutor tutor,
                        SubscriptionPlan plan,
                        LocalDate startDate,
                        LocalDate endDate,
                        boolean isActive,
                        double amount,
                        String razorpaySubscriptionId) {

        this.tutor = tutor;
        this.plan = plan;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.amount = amount;
        this.razorpaySubscriptionId = razorpaySubscriptionId;
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

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public void setPlan(SubscriptionPlan plan) {
        this.plan = plan;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRazorpaySubscriptionId() {
        return razorpaySubscriptionId;
    }

    public void setRazorpaySubscriptionId(String razorpaySubscriptionId) {
        this.razorpaySubscriptionId = razorpaySubscriptionId;
    }
}
