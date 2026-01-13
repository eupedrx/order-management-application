package com.example.testelynx.docs;


import com.example.testelynx.domain.Orders;
import com.example.testelynx.dto.CreateOrdersDTO;
import com.example.testelynx.dto.OrderResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Pedidos", description = "Operações relacionadas a pedidos")
public interface OrdersControllerDoc {


    @Operation(
        summary = "Listar Pedidos",
        description = "Lista todos os pedidos cadastrados no sistema"

    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public List<Orders> listarOrders();

    @Operation(
        summary = "Buscar Pedido por ID",
        description = "Busca um pedido específico pelo seu ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<OrderResponseDTO> buscarOrder(@Parameter(description = "Id do Pedido", required = true) Long id);

    @Operation(
        summary = "Criar Pedido",
        description = "Cria um novo pedido no sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public Orders criarOrder(@RequestBody CreateOrdersDTO dto);


    @Operation(
        summary = "Cancelar Pedido",
        description = "Cancela um pedido específico pelo seu ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido cancelado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<?> cancelOrder(@Parameter(description = "Id do Pedido", required = true) Long id);

}
