package com.example.omp.controller;


import com.example.omp.docs.CustomersControllerDoc;
import com.example.omp.domain.Customers;
import com.example.omp.service.CustomersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomersController implements CustomersControllerDoc {

    private final CustomersService customersService;

    public CustomersController(CustomersService customersService) {
        this.customersService = customersService;
    }

    @GetMapping
    @Override
    public List<Customers> listarCustomers() {
        return customersService.listarCustomers();
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Customers> buscarCustomerPorId(Long id) {
        return customersService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Override
    public Customers salvarCustomer(@RequestBody Customers customer) {
        return customersService.salvarCustomer(customer);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deletarCustomer(@PathVariable Long id) {
        customersService.deletarCustomer(id);
        return ResponseEntity.noContent().build();
    }

}
