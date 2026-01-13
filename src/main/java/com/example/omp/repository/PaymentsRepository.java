package com.example.omp.repository;

import com.example.omp.domain.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {

}
