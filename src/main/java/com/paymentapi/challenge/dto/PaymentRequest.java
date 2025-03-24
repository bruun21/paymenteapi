package com.paymentapi.challenge.dto;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import com.paymentapi.challenge.enums.PaymentMethod;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PaymentRequest {
    @NotNull
    private Integer codigoDebito;

    @CPF
    private String cpf;

    @CNPJ
    private String cnpj;

    @NotNull
    private PaymentMethod metodo;

    @Size(min = 13, max = 19)
    private String numeroCartao;

    @Positive
    private BigDecimal valor;

    @AssertTrue(message = "Informe apenas CPF ou CNPJ, mas nunca os dois ao mesmo tempo.")
    public boolean isValidCpfOrCnpj() {
        boolean hasCpf = cpf != null && !cpf.isBlank();
        boolean hasCnpj = cnpj != null && !cnpj.isBlank();
        return hasCpf ^ hasCnpj;
    }
}