package com.project.tutorplatform.mapper;

import com.project.tutorplatform.dto.response.PaymentResponse;
import com.project.tutorplatform.entity.Payment;

public class PaymentMapper {

    public static PaymentResponse toResponse(Payment payment) {

        if (payment == null) return null;

        PaymentResponse res = new PaymentResponse();

        res.setId(payment.getId());
        res.setBookingId(payment.getBooking().getId());
        res.setAmount(payment.getAmount());
        res.setCurrency(payment.getCurrency());
        res.setStatus(payment.getStatus());
        res.setPaymentMethod(payment.getPaymentMethod());
        res.setRazorpayOrderId(payment.getRazorpayOrderId());
        res.setRazorpayPaymentId(payment.getRazorpayPaymentId());
        res.setRefundAmount(payment.getRefundId());
        res.setRefundAmount(payment.getRefundAmount());
        res.setPaidAt(payment.getPaidAt());

        return res;
    }
}