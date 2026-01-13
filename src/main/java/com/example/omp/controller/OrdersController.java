package com.example.omp.controller;


import com.example.omp.docs.OrdersControllerDoc;
import com.example.omp.domain.Orders;
import com.example.omp.dto.CreateOrdersDTO;
import com.example.omp.dto.OrderResponseDTO;
import com.example.omp.service.OrdersService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrdersController implements OrdersControllerDoc {
    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping
    @Override
    public List<Orders> listarOrders() {
        return ordersService.listarOrders();
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<OrderResponseDTO> buscarOrder(@PathVariable Long id) {
        return ordersService.findOrderById(id) // Service retorna DTO
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Override
    public Orders criarOrder(@RequestBody CreateOrdersDTO dto) {
        return ordersService.criarOrder(dto);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        try {
            OrderResponseDTO response = ordersService.cancelOrder(id); // Service retorna DTO
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}