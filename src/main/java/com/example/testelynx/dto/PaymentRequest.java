package com.example.testelynx.dto;

import com.example.testelynx.domain.enums.PaymentMethod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


// Records para diminuir o boilerplate de classes DTO.
public record PaymentRequest(
        @NotNull
        Long orderId,
        @NotNull
        PaymentMethod method,
        @NotNull @Min(1)
        Integer amountCents
) {}
