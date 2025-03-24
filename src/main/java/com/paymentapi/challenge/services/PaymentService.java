package com.paymentapi.challenge.services;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.paymentapi.challenge.dto.PaymentRequest;
import com.paymentapi.challenge.dto.PaymentStatusUpdate;
import com.paymentapi.challenge.enums.PaymentMethod;
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

    @Transactional
    public Payment createPayment(PaymentRequest request) {
        Payment payment = dtoToModel(request);
        validaCartao(payment);
        return repository.save(payment);
    }

    private void validaCartao(Payment payment) {
        if ((payment.getMetodo() == PaymentMethod.CARTAO_CREDITO || payment.getMetodo() == PaymentMethod.CARTAO_CREDITO)
                && ObjectUtils.isEmpty(payment.getNumeroCartao())) {
            throw new IllegalStateException("Número do cartão é obrigatório para este método de pagamento");
        }
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

    public Payment dtoToModel(PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        payment.setCodigoDebito(paymentRequest.getCodigoDebito());
        payment.setCnpj(paymentRequest.getCnpj());
        payment.setCpf(paymentRequest.getCpf());
        payment.setMetodo(paymentRequest.getMetodo());
        payment.setNumeroCartao(paymentRequest.getNumeroCartao());
        payment.setValor(paymentRequest.getValor());
        payment.setStatus(PaymentStatus.PENDENTE_PROCESSAMENTO);
        payment.setAtivo(true);
        return payment;
    }

    public void logicalDelete(Long id) throws BadRequestException {
        Payment payment = repository.findById(id).orElseThrow();
        validaDelete(payment);
        payment.setAtivo(false);
        repository.save(payment);
    }

    public void validaDelete(Payment payment) throws BadRequestException {
        if (!payment.getStatus().equals(PaymentStatus.PENDENTE_PROCESSAMENTO)) {
            throw new BadRequestException(
                    "Um pagamento só pode ser deletado em preocessos com status pendente de processamento");
        }
    }
}