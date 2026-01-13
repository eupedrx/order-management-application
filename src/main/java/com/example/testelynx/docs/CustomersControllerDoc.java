package com.example.testelynx.docs;

import com.example.testelynx.domain.Customers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.annotations.PropertyRef;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Clientes", description = "Operações relacionadas a clientes")
public interface CustomersControllerDoc {


    @Operation(
        summary = "Listar Clientes",
        description = "Lista todos os clientes cadastrados no sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public List<Customers> listarCustomers();

    @Operation(
        summary = "Buscar Cliente por ID",
        description = "Busca um cliente específico pelo seu ID"
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Customers> buscarCustomerPorId(@Parameter(description = "Id do Cliente", required = true) Long id);

    @Operation(
        summary = "Salvar Cliente",
        description = "Salva um novo cliente no sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public Customers salvarCustomer(@RequestBody Customers customer);

    @Operation(
        summary = "Deletar Cliente",
        description = "Deleta um cliente específico pelo seu ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Void> deletarCustomer(@Parameter(description = "Id do Cliente a ser deletado", required = true) Long id);


}

