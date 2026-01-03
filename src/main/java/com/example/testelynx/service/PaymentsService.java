package com.example.testelynx.service;


import com.example.testelynx.domain.Orders;
import com.example.testelynx.domain.Payments;
import com.example.testelynx.domain.enums.OrderStatus;
import com.example.testelynx.domain.enums.PaymentMethod;
import com.example.testelynx.repository.OrdersRepository;
import com.example.testelynx.repository.PaymentsRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentsService {

    private final PaymentsRepository paymentRepository;
    private final OrdersRepository orderRepository;

    // Construtor para injeção de dependência
    public PaymentsService(PaymentsRepository paymentRepository,
                           OrdersRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }


    // Processar pagamento
    public Payments processarPagamento(Long orderId, Payments payment) {

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Pedido não encontrado com ID: " + orderId));

        if (order.getStatus() != OrderStatus.NEW) {
            throw new IllegalStateException(
                    "O pedido não pode ser pago. Atual status: " + order.getStatus());
        }

        Integer orderCents = order.calculateTotal();
        if (payment.getAmountCents() < orderCents) {
            throw new IllegalArgumentException(
                    "Valor do pagamento insuficiente: esperado pelo menos " + orderCents + " cents.");
        }

        payment.setOrder(order);
        payment.setPaidAt(LocalDateTime.now());

        Payments savedPayment = paymentRepository.save(payment);

        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);

        return savedPayment;
    }

}