package com.example.testelynx.dto;

import com.example.testelynx.domain.enums.OrderStatus;

import java.util.List;


// Records para diminuir o boilerplate de classes DTO.
public record CreateOrdersDTO(
        Long customerId,
        List<CreateOrdersItemDTO> items
) {}

