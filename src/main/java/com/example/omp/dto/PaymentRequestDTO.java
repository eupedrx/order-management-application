package com.example.omp.dto;

import com.example.omp.domain.enums.PaymentMethod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


// Records para diminuir o boilerplate de classes DTO.
public record PaymentRequestDTO(
        @NotNull
        Long orderId,
        @NotNull
        PaymentMethod method,
        @NotNull @Min(1)
        Integer amountCents
) {}
