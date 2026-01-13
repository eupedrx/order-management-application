package com.example.testelynx.docs;


import com.example.testelynx.domain.Products;
import com.example.testelynx.dto.ProductsFilterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Produtos", description = "Operações relacionadas a produtos")
public interface ProductsControllerDoc {

    @Operation(
        summary = "Listar Produtos",
        description = "Lista todos os produtos com base nos filtros fornecidos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })

    public List<Products> listarProdutos(ProductsFilterDTO filtro);

    @Operation(
        summary = "Buscar Produto por ID",
        description = "Busca um produto específico pelo seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<Products> buscarProduto(@Parameter(description = "Id do Produto", required = true) Long id);

    @Operation(
        summary = "Salvar Produto",
        description = "Salva um novo produto no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public Products salvarProduto(@RequestBody Products produto);

    @Operation(
        summary = "Deletar Produto",
        description = "Deleta um produto específico pelo seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<Void> deletarProduto(@Parameter(description = "Id do Produto", required = true) Long id);
}
