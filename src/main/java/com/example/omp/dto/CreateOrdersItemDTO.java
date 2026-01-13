package com.example.omp.dto;


// Records para diminuir o boilerplate de classes DTO.
public record CreateOrdersItemDTO(
        Long productId,
        Integer quantity
) {}
