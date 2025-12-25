package com.example.testelynx.controller;

import com.example.testelynx.domain.Payments;
import com.example.testelynx.dto.PaymentRequest;
import com.example.testelynx.service.PaymentsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentsController {

    private final PaymentsService paymentService;

    public PaymentsController(PaymentsService paymentService) {
        this.paymentService = paymentService;
    }



    @PostMapping
    public ResponseEntity<Payments> processPayment(@RequestBody PaymentRequest request) {
        Payments processedPayment = paymentService.processarPagamento(
                request.orderId(),
                request.method(),
                request.amountCents()
        );
        return new ResponseEntity<>(processedPayment, HttpStatus.CREATED);
    }
}
