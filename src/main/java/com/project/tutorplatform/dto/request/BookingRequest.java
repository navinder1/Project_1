package com.project.tutorplatform.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import com.project.tutorplatform.enums.TeachingMode;

public class BookingRequest {

    private Long tutorId;
    private Long subjectId;
    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private TeachingMode teachingMode;
    private String address;
    private String meetingLink;
    private String notes;

    public Long getTutorId() {
        return tutorId;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public TeachingMode getTeachingMode() {
        return teachingMode;
    }

    public void setTeachingMode(TeachingMode teachingMode) {
        this.teachingMode = teachingMode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}