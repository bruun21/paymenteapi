package com.paymentapi.challenge.model;

import java.math.BigDecimal;

import org.hibernate.annotations.Check;

import com.paymentapi.challenge.enums.PaymentMethod;
import com.paymentapi.challenge.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Check(constraints = "(cpf IS NOT NULL AND cnpj IS NULL) OR (cpf IS NULL AND cnpj IS NOT NULL)")
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer codigoDebito;

    @NotBlank
    private String cpf;

    @NotBlank
    private String cnpj;

    @Enumerated(EnumType.STRING)
    private PaymentMethod metodo;

    private String numeroCartao;

    @Positive
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDENTE_PROCESSAMENTO;

    private boolean ativo = true;

}