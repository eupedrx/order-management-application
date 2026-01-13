package com.example.testelynx.controller;

import com.example.testelynx.docs.PaymentsControllerDoc;
import com.example.testelynx.domain.Payments;
import com.example.testelynx.dto.PaymentRequestDTO;
import com.example.testelynx.mapper.PaymentMapper;
import com.example.testelynx.service.PaymentsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentsController implements PaymentsControllerDoc {

    private final PaymentsService paymentService;
    private final PaymentMapper paymentMapper;

    public PaymentsController(PaymentsService paymentService, PaymentMapper paymentMapper) {
        this.paymentMapper = paymentMapper;
        this.paymentService = paymentService;
    }

    @PostMapping
    @Override
    public ResponseEntity<Payments> processPayment(
            @Valid @RequestBody PaymentRequestDTO request) {

        Payments payment = paymentMapper.toEntity(request);
        Payments processedPayment =
                paymentService.processarPagamento(request.orderId(), payment);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(processedPayment);
    }
}
