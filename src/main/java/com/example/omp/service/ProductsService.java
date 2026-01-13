package com.example.omp.service;

import com.example.omp.domain.Products;
import com.example.omp.dto.ProductsFilterDTO;
import com.example.omp.repository.ProductsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }


    // Listar todos os Produtos
    public List<Products> listarProdutos() {
        return productsRepository.findAll();
    }

    // Listar Produtos com Filtros (Utilizando DTO de Filtros)
    public List<Products> listarProdutos(ProductsFilterDTO filtro) {
        return productsRepository.buscarComFiltros(
                filtro.name(),
                filtro.category(),
                filtro.priceMinCents(),
                filtro.priceMaxCents(),
                filtro.active()
        );
    }

    // Buscar Produto por ID
    public Optional<Products> findById(Long id) {
        return productsRepository.findById(id);
    }


    // Criar produto no Banco de Dados
    public Products salvarProduto(Products produto) {
        return productsRepository.save(produto);
    }


    // Deletar Produto por ID
    public void deletarProduto(Long id) {
        if (!productsRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }
        productsRepository.deleteById(id);
    }

}
