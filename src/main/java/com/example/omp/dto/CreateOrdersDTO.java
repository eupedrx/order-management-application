package com.example.omp.dto;

import java.util.List;


// Records para diminuir o boilerplate de classes DTO.
public record CreateOrdersDTO(
        Long customerId,
        List<CreateOrdersItemDTO> items
) {}

