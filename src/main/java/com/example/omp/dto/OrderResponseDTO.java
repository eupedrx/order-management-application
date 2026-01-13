package com.example.omp.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        String status,
        Integer totalCents,
        LocalDateTime createdAt,
        Long customerId,
        List<OrderItemResponseDTO> items
) {}