package com.paymentapi.challenge.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymentapi.challenge.dto.PaymentRequest;
import com.paymentapi.challenge.enums.PaymentStatus;
import com.paymentapi.challenge.model.Payment;
import com.paymentapi.challenge.services.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pagamentos")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> listPayments(
            @RequestParam(required = false) Integer codigoDebito,
            @RequestParam(required = false) String pagador,
            @RequestParam(required = false) PaymentStatus status) {

        return ResponseEntity.ok(paymentService.listPayments(codigoDebito, pagador, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> logicalDelete(@PathVariable Long id) throws BadRequestException {
        paymentService.logicalDelete(id);
        return ResponseEntity.noContent().build();
    }
}