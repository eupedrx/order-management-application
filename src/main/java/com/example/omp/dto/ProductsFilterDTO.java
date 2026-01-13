package com.example.omp.dto;



// Records para diminuir o boilerplate de classes DTO.
public record ProductsFilterDTO(
        String name,
        String category,
        Integer priceMinCents,
        Integer priceMaxCents,
        Boolean active
) {}