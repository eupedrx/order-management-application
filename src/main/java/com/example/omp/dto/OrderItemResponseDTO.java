package com.example.omp.dto;

public record OrderItemResponseDTO(
        Long productId,
        String productName,
        Integer quantity,
        Integer priceCents
) {}