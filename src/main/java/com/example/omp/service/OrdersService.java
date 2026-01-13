package com.example.omp.service;

import com.example.omp.domain.Customers;
import com.example.omp.domain.OrderItems;
import com.example.omp.domain.Orders;
import com.example.omp.domain.Products;
import com.example.omp.domain.enums.OrderStatus;
import com.example.omp.dto.CreateOrdersDTO;
import com.example.omp.dto.CreateOrdersItemDTO;
import com.example.omp.dto.OrderResponseDTO;
import com.example.omp.mapper.OrderMapper;
import com.example.omp.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class OrdersService {

    private final OrdersRepository orderRepository;
    private final ProductsRepository productRepository;
    private final CustomersRepository customerRepository;
    private final OrderMapper orderMapper;

    // Construtor
    public OrdersService(OrdersRepository orderRepository,
                         ProductsRepository productRepository,
                         CustomersRepository customerRepository,
                         OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.orderMapper = orderMapper;
    }

    // Listar todos os pedidos
    public List<Orders> listarOrders() {
        return orderRepository.findAll();
    }

    // Lista por ID do pedido (Utilizando DTO)
    public Optional<OrderResponseDTO> findOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDTO);
    }

    public Orders criarOrder(CreateOrdersDTO dto) {

        Customers customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Orders order = new Orders();
        order.setCustomers(customer);
        order.setStatus(OrderStatus.NEW);

        List<OrderItems> items = new ArrayList<>();

        for (CreateOrdersItemDTO itemDTO : dto.items()) {

            Products product = productRepository.findById(itemDTO.productId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            // REGRA DE NEGÓCIO - Produto deve estar ativo
            if (!Boolean.TRUE.equals(product.getActive())) {
                throw new RuntimeException(
                        "Produto inativo: " + product.getName()
                );
            }

            // REGRA DE NEGÓCIO - Quantidade deve ser maior que zero
            if (itemDTO.quantity() <= 0) {
                throw new RuntimeException("Quantidade inválida");
            }

            OrderItems item = new OrderItems();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDTO.quantity());
            // Preço sempre do banco de dados.
            item.setUnitPriceCents(product.getPriceCents());

            items.add(item);
        }

        order.setItems(items);

        return orderRepository.save(order);
    }

    public OrderResponseDTO cancelOrder(Long orderId) {
        // Buscar o pedido
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        // REGRA DE NEGÓCIO: Verificar se pode cancelar
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Pedido já está cancelado");
        }

        // Cancelar o pedido
        order.setStatus(OrderStatus.CANCELLED);
        Orders savedOrder = orderRepository.save(order);

        return orderMapper.toDTO(savedOrder);
    }
}
