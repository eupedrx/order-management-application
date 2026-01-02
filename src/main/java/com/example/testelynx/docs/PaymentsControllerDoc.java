package com.example.testelynx.docs;


import com.example.testelynx.domain.Payments;
import com.example.testelynx.dto.PaymentRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Pagamentos", description = "Operações relacionadas a pagamentos")
public interface PaymentsControllerDoc {


    @Operation(
            summary = "Processar Pagamento",
            description = "Processa um pagamento com base nos detalhes fornecidos na solicitação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pagamento processado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Payments> processPayment(@RequestBody PaymentRequestDTO request);

}
