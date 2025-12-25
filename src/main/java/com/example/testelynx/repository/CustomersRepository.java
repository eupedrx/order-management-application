package com.example.testelynx.repository;

import com.example.testelynx.domain.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomersRepository extends JpaRepository<Customers, Long> {

}
