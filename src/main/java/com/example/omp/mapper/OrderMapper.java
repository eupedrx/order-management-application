package com.example.omp.mapper;

import com.example.omp.domain.Orders;
import com.example.omp.domain.OrderItems;
import com.example.omp.dto.OrderResponseDTO;
import com.example.omp.dto.OrderItemResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "status", expression = "java(order.getStatus().name())")
    @Mapping(target = "totalCents", expression = "java(order.calculateTotal())")
    @Mapping(target = "customerId", source = "customers.id")
    OrderResponseDTO toDTO(Orders order);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    OrderItemResponseDTO toItemDTO(OrderItems item);
}