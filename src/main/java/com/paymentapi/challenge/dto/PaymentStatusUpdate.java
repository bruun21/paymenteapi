package com.paymentapi.challenge.dto;

import com.paymentapi.challenge.enums.PaymentStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
@Getter
public class PaymentStatusUpdate {
    @NotNull
    private PaymentStatus novoStatus;
}