package com.example.testelynx.service;

import com.example.testelynx.domain.Customers;
import com.example.testelynx.repository.CustomersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomersService {

    private final CustomersRepository customersRepository;

    public CustomersService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    // Listar todos os Clientes
    public List<Customers> listarCustomers(){
        return customersRepository.findAll();
    }

    // Listar cliente por ID
    public Optional<Customers> findById(Long id){
        return customersRepository.findById(id);
    }

    // Salvar Cliente
    public Customers salvarCustomer(Customers customer){
        return customersRepository.save(customer);
    }

    // Deletar Cliente por ID
    public void deletarCustomer(Long id){
        if (!customersRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado");
        }
        customersRepository.deleteById(id);
    }


}
