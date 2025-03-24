package com.paymentapi.challenge.dto;

import com.paymentapi.challenge.enums.PaymentStatus;

import jakarta.validation.constraints.NotNull;

public class PaymentStatusUpdate {
    @NotNull
    private PaymentStatus novoStatus;
}