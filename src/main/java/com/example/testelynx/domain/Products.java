package com.example.testelynx.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 60)
    private String category;

    @Column(name = "price_cents", nullable = false)
    private Integer priceCents;

    @Column(nullable = false)
    private Boolean active = true;

    // Construtores
    public Products() {
    }

    public Products(String name, String category, Integer priceCents, Boolean active) {
        this.name = name;
        this.category = category;
        this.priceCents = priceCents;
        this.active = active;
    }
    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPriceCents() {
        return priceCents;
    }

    public void setPriceCents(Integer priceCents) {
        this.priceCents = priceCents;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    // Validação
    @PrePersist
    @PreUpdate
    private void validatePrice() {
        if (priceCents != null && priceCents < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
    }
}
