package com.example.omp.mapper;

import com.example.omp.domain.Payments;
import com.example.omp.dto.PaymentRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payments toEntity(PaymentRequestDTO dto) {
        Payments payment = new Payments();
        payment.setMethod(dto.method());
        payment.setAmountCents(dto.amountCents());
        return payment;
    }
}
