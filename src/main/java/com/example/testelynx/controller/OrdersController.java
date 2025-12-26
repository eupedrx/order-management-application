package com.example.testelynx.controller;


import com.example.testelynx.domain.Orders;
import com.example.testelynx.dto.CreateOrdersDTO;
import com.example.testelynx.dto.OrderItemResponseDTO;
import com.example.testelynx.dto.OrderResponseDTO;
import com.example.testelynx.service.OrdersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrdersController {
    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }


    @GetMapping
    public List<Orders> listarOrders() {
        return ordersService.listarOrders();
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> buscarOrder(@PathVariable Long id) {
        return ordersService.findById(id)
                .map(order -> mapToDTO(order)) // Apenas mapeia
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private OrderResponseDTO mapToDTO(Orders order) {
        List<OrderItemResponseDTO> itemsDTO = order.getItems().stream()
                .map(item -> new OrderItemResponseDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getSubtotal()
                ))
                .toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getStatus().name(),
                order.calculateTotal(),
                order.getCustomers().getId(),
                itemsDTO
        );
    }

    @PostMapping
    public Orders criarOrder(@RequestBody CreateOrdersDTO dto) {
        return ordersService.criarOrder(dto);
    }

}