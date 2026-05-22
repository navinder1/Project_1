package com.project.tutorplatform.controller;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.tutorplatform.dto.request.PaymentRequest;
import com.project.tutorplatform.dto.response.PaymentResponse;
import com.project.tutorplatform.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<@Nullable Object> createPayment(
            @RequestBody PaymentRequest request) {
    	return ResponseEntity.ok(
                paymentService.createPayment(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<@Nullable Object> getPayment(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                paymentService.getPayment(id)
        );
    }
}
