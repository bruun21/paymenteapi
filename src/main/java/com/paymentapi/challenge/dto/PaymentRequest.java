package com.paymentapi.challenge.dto;


import java.math.BigDecimal;

import com.paymentapi.challenge.model.Payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class PaymentRequest {
    @NotNull
    private Integer codigoDebito;
    
    @NotBlank
    private String pagadorCpfCnpj;
    
    @NotNull
    private Payment.PaymentMethod metodo;
    
    @Size(min = 13, max = 19)
    private String numeroCartao;
    
    @Positive
    private BigDecimal valor;
}