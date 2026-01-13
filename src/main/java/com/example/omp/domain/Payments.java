package com.example.testelynx.domain;

import com.example.testelynx.domain.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount_cents", nullable = false)
    private Integer amountCents;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    // Relacionamentos
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    // Enumerados
    @NotNull(message = "Tipo de pagamaneto é necessário.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentMethod method;


    // Construtores
    public Payments() {
    }

    public Payments(PaymentMethod method, Integer amountCents, LocalDateTime paidAt, Orders order) {
        this.method = method;
        this.amountCents = amountCents;
        this.paidAt = paidAt;
        this.order = order;
    }



    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public Integer getAmountCents() {
        return amountCents;
    }

    public void setAmountCents(Integer amountCents) {
        this.amountCents = amountCents;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    // Validação
    @PrePersist
    @PreUpdate
    private void validate() {
        if (amountCents != null && amountCents < 0) {
            throw new IllegalArgumentException("Valor do pagamento não pode ser negativo");
        }
    }
}