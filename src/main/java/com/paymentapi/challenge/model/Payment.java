package com.paymentapi.challenge.model;

import java.math.BigDecimal;


import jakarta.persistence.Id;

import com.paymentapi.challenge.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer codigoDebito;

    @NotBlank
    private String pagadorCpfCnpj;

    @Enumerated(EnumType.STRING)
    private PaymentMethod metodo;

    private String numeroCartao;

    @Positive
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDENTE_PROCESSAMENTO;

    private boolean ativo = true;

    public enum PaymentMethod {
        BOLETO, PIX, CARTAO_CREDITO, CARTAO_DEBITO
    }

}