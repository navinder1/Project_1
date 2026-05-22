package com.project.tutorplatform.mapper;

import com.project.tutorplatform.dto.response.BookingResponse;
import com.project.tutorplatform.entity.Booking;

public class BookingMapper {

    public static BookingResponse toResponse(Booking booking) {

        if (booking == null) return null;

        BookingResponse res = new BookingResponse();

        res.setId(booking.getId());
        res.setBookingNumber(booking.getBookingNumber());
        res.setStudentId(booking.getStudent().getId());
        res.setTutorId(booking.getTutor().getId());
        res.setSubjectId(booking.getSubject().getId());
        res.setBookingDate(booking.getBookingDate());
        res.setStartTime(booking.getStartTime());
        res.setEndTime(booking.getEndTime());
        res.setDurationMinutes(booking.getDurationMinutes());
        res.setTeachingMode(booking.getTeachingMode());
        res.setAddress(booking.getAddress());
        res.setMeetingLink(booking.getMeetingLink());
        res.setStatus(booking.getStatus());
        res.setTotalAmount(booking.getTotalAmount());
        res.setPlatformFee(booking.getPlatformFee());
        res.setNotes(booking.getNotes());
        res.setCreatedAt(booking.getCreatedAt());

        return res;
    }
}