package com.paymentapi.challenge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentapi.challenge.enums.PaymentStatus;
import com.paymentapi.challenge.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByCodigoDebitoAndAtivoTrue(Integer codigoDebito);

    List<Payment> findByPagadorCpfCnpjAndAtivoTrue(String pagadorCpfCnpj);

    List<Payment> findByStatusAndAtivoTrue(PaymentStatus status);
}