package com.example.testelynx.repository;

import com.example.testelynx.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {


    @Query("""
        SELECT p FROM Products p
        WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:category IS NULL OR p.category = :category)
          AND (:priceMinCents IS NULL OR p.priceCents >= :priceMinCents)
          AND (:priceMaxCents IS NULL OR p.priceCents <= :priceMaxCents)
          AND (:active IS NULL OR p.active = :active)
    """)
    List<Products> buscarComFiltros(
            @Param("name") String name,
            @Param("category") String category,
            @Param("priceMinCents") Integer priceMinCents,
            @Param("priceMaxCents") Integer priceMaxCents,
            @Param("active") Boolean active
    );

}
