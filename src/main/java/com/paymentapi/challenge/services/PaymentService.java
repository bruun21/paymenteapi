package com.paymentapi.challenge.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.paymentapi.challenge.dto.PaymentRequest;
import com.paymentapi.challenge.dto.PaymentStatusUpdate;
import com.paymentapi.challenge.enums.PaymentStatus;
import com.paymentapi.challenge.model.Payment;
import com.paymentapi.challenge.repository.PaymentRepository;
import com.paymentapi.challenge.specification.PaymentSpecification;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;

    public Payment createPayment(PaymentRequest request) {
        Payment payment = new Payment();
        return repository.save(payment);
    }

    @Transactional
    public Payment updateStatus(Long id, PaymentStatusUpdate update) {
        Payment payment = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Pagamento não encontrado com ID: " + id));

        validarMudancaStatus(payment, update.getNovoStatus());

        payment.setStatus(update.getNovoStatus());
        return repository.save(payment);
    }

    private void validarMudancaStatus(Payment payment, PaymentStatus novoStatus) {
        PaymentStatus statusAtual = payment.getStatus();

        if (statusAtual == PaymentStatus.PROCESSADO_SUCESSO) {
            throw new IllegalStateException("Pagamento já processado com sucesso não pode ser alterado");
        }

        if (statusAtual == PaymentStatus.PROCESSADO_FALHA && novoStatus != PaymentStatus.PENDENTE_PROCESSAMENTO) {
            throw new IllegalStateException("Pagamento com falha só pode ser alterado para pendente de processamento");
        }
    }

    public List<Payment> listPayments(Integer codigoDebito, String pagadorCpfCnpj, PaymentStatus status) {
        return repository.findAll(PaymentSpecification.filterPayments(codigoDebito, pagadorCpfCnpj, status));
    }
}