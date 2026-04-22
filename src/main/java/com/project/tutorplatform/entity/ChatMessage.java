package com.project.tutorplatform.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Sender of the message
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    // Receiver of the message
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    // Optional booking reference
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = true)
    private Booking booking;

    // Message content
    @Column(nullable = false, length = 1000)
    private String content;

    // Read status
    @Column(nullable = false)
    private boolean isRead = false;

    // Sent timestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime sentAt;

    // Default Constructor
    public ChatMessage() {
    }

    // Parameterized Constructor
    public ChatMessage(User sender,
                       User receiver,
                       Booking booking,
                       String content) {

        this.sender = sender;
        this.receiver = receiver;
        this.booking = booking;
        this.content = content;
    }

    // Automatically set sent time
    @PrePersist
    public void setSentAt() {
        this.sentAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }
}