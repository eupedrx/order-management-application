package com.example.testelynx.controller;

import com.example.testelynx.domain.Products;
import com.example.testelynx.dto.ProductsFilterDTO;
import com.example.testelynx.service.ProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public List<Products> listarProdutos(ProductsFilterDTO filtro) {
        return productsService.listarProdutos(filtro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Products> buscarProduto(@PathVariable Long id) {
        return productsService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Products salvarProduto(@RequestBody Products produto) {
        return productsService.salvarProduto(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        productsService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

}
