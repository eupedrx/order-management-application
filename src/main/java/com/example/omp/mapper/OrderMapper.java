package com.example.omp.mapper;

import com.example.omp.domain.Orders;
import com.example.omp.domain.OrderItems;
import com.example.omp.dto.OrderResponseDTO;
import com.example.omp.dto.OrderItemResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponseDTO toDTO(Orders order) {
        if (order == null) {
            return null;
        }

        List<OrderItemResponseDTO> itemsDTO = order.getItems().stream()
                .map(this::toItemDTO)
                .collect(Collectors.toList());

        return new OrderResponseDTO(
                order.getId(),
                order.getStatus().name(),
                order.calculateTotal(),
                order.getCreatedAt(),
                order.getCustomers().getId(),
                itemsDTO
        );
    }

    public OrderItemResponseDTO toItemDTO(OrderItems item) {
        if (item == null) {
            return null;
        }

        return new OrderItemResponseDTO(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getSubtotal()
        );
    }

    public List<OrderResponseDTO> toDTOList(List<Orders> orders) {
        if (orders == null || orders.isEmpty()) {
            return List.of();
        }

        return orders.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
