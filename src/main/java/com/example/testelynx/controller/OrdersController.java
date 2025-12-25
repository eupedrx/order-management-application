package com.example.testelynx.controller;


import com.example.testelynx.domain.Orders;
import com.example.testelynx.dto.CreateOrdersDTO;
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
    public ResponseEntity<Orders> buscarOrder(@PathVariable Long id) {
        return ordersService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Orders criarOrder(@RequestBody CreateOrdersDTO dto) {
        return ordersService.criarOrder(dto);
    }

}