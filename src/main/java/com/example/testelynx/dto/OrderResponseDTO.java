package com.example.testelynx.dto;

import java.util.List;

public record OrderResponseDTO(
        Long id,
        String status,
        Integer totalCents,
        Long customerId,
        List<OrderItemResponseDTO> items
) {}