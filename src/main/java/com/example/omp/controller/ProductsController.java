package com.example.omp.controller;

import com.example.omp.docs.ProductsControllerDoc;
import com.example.omp.domain.Products;
import com.example.omp.dto.ProductsFilterDTO;
import com.example.omp.service.ProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductsController implements ProductsControllerDoc {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    @Override
    public List<Products> listarProdutos(ProductsFilterDTO filtro) {
        return productsService.listarProdutos(filtro);
    }

    @GetMapping("/{id}")
    @Override
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
