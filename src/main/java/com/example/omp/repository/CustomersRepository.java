package com.example.omp.repository;

import com.example.omp.domain.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomersRepository extends JpaRepository<Customers, Long> {

}
