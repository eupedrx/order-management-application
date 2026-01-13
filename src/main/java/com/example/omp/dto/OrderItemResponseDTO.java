package com.example.testelynx.dto;

public record OrderItemResponseDTO(
        Long productId,
        String productName,
        Integer quantity,
        Integer priceCents
) {}