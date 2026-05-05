package com.project.tutorplatform.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.project.tutorplatform.dto.request.PaymentRequest;
import com.project.tutorplatform.dto.response.PaymentResponse;
import com.project.tutorplatform.entity.Booking;
import com.project.tutorplatform.entity.Payment;
import com.project.tutorplatform.enums.BookingStatus;
import com.project.tutorplatform.enums.PaymentStatus;
import com.project.tutorplatform.repository.BookingRepository;
import com.project.tutorplatform.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    public PaymentResponse createOrder(PaymentRequest req) {

        Booking booking = bookingRepository.findById(req.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(req.getAmount());
        payment.setCurrency(req.getCurrency() != null ? req.getCurrency() : "INR");
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentMethod(req.getPaymentMethod());

        String orderId = "order_" + UUID.randomUUID().toString().substring(0, 10);
        payment.setRazorpayOrderId(orderId);

        paymentRepository.save(payment);

        return mapToResponse(payment);
    }

    public PaymentResponse verifyPayment(String orderId, String paymentId, String signature) {

        Payment payment = paymentRepository.findByRazorpayOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setRazorpayPaymentId(paymentId);
        payment.setRazorpaySignature(signature);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaidAt(LocalDateTime.now());

        Booking booking = payment.getBooking();
        booking.setStatus(BookingStatus.CONFIRMED);

        bookingRepository.save(booking);
        paymentRepository.save(payment);

        return mapToResponse(payment);
    }

    public void markFailed(String orderId) {

        Payment payment = paymentRepository.findByRazorpayOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.FAILED);

        paymentRepository.save(payment);
    }

    public PaymentResponse refund(Long paymentId, BigDecimal amount) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (!payment.getStatus().equals(PaymentStatus.SUCCESS)) {
            throw new RuntimeException("Refund not allowed");
        }

        payment.setRefundAmount(amount);
        payment.setRefundId("refund_" + UUID.randomUUID().toString().substring(0, 10));
        payment.setStatus(PaymentStatus.REFUNDED);

        Booking booking = payment.getBooking();
        booking.setStatus(BookingStatus.CANCELLED);

        bookingRepository.save(booking);
        paymentRepository.save(payment);

        return mapToResponse(payment);
    }

    private PaymentResponse mapToResponse(Payment payment) {

        PaymentResponse res = new PaymentResponse();

        res.setId(payment.getId());
        res.setBookingId(payment.getBooking().getId());
        res.setRazorpayOrderId(payment.getRazorpayOrderId());
        res.setRazorpayPaymentId(payment.getRazorpayPaymentId());
        res.setAmount(payment.getAmount());
        res.setCurrency(payment.getCurrency());
        res.setStatus(payment.getStatus());
        res.setPaymentMethod(payment.getPaymentMethod());
        res.setRefundAmount(payment.getRefundAmount());
        res.setPaidAt(payment.getPaidAt());
        res.setCreatedAt(payment.getCreatedAt());

        return res;
    }
}