package com.paymentapi.challenge.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.paymentapi.challenge.enums.PaymentStatus;
import com.paymentapi.challenge.model.Payment;

import jakarta.persistence.criteria.Predicate;

public class PaymentSpecification {
    public static Specification<Payment> filterPayments(Integer codigoDebito, String pagadorCpfCnpj, PaymentStatus status) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.isTrue(root.get("ativo")));

            if (codigoDebito != null) {
                predicates.add(criteriaBuilder.equal(root.get("codigoDebito"), codigoDebito));
            }
            if (pagadorCpfCnpj != null) {
                predicates.add(criteriaBuilder.equal(root.get("pagadorCpfCnpj"), pagadorCpfCnpj));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
