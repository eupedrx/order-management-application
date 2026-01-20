package com.example.omp.mapper;

import com.example.omp.domain.Payments;
import com.example.omp.dto.PaymentRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "method", source = "method")
    @Mapping(target = "amountCents", source = "amountCents")
    Payments toEntity(PaymentRequestDTO dto);
}
