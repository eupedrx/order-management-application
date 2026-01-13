package com.example.testelynx.mapper;

import com.example.testelynx.domain.Payments;
import com.example.testelynx.dto.PaymentRequestDTO;
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
