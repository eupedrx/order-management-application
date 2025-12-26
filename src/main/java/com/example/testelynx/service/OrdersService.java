package com.example.testelynx.service;

import com.example.testelynx.domain.Customers;
import com.example.testelynx.domain.OrderItems;
import com.example.testelynx.domain.Orders;
import com.example.testelynx.domain.Products;
import com.example.testelynx.domain.enums.OrderStatus;
import com.example.testelynx.dto.CreateOrdersDTO;
import com.example.testelynx.dto.CreateOrdersItemDTO;
import com.example.testelynx.dto.OrderItemResponseDTO;
import com.example.testelynx.dto.OrderResponseDTO;
import com.example.testelynx.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class OrdersService {

    private final OrdersRepository orderRepository;
    private final ProductsRepository productRepository;
    private final CustomersRepository customerRepository;

    // Construtor
    public OrdersService(OrdersRepository orderRepository,
                         ProductsRepository productRepository,
                         CustomersRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public List<Orders> listarOrders() {
        return orderRepository.findAll();
    }

    public Optional<Orders> findById(Long id) {
        return orderRepository.findById(id);
    }

    // Método que retorna DTO
    public Optional<OrderResponseDTO> findOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::toDTO);
    }

    // Método privado de conversão para DTO
    private OrderResponseDTO toDTO(Orders order) {
        List<OrderItemResponseDTO> itemsDTO = order.getItems().stream()
                .map(item -> new OrderItemResponseDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getSubtotal()
                ))
                .toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getStatus().name(),
                order.calculateTotal(),
                order.getCreatedAt(),
                order.getCustomers().getId(),
                itemsDTO
        );
    }

    @Transactional
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

    @Transactional
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

        // ⚠️ Retorna DTO
        return toDTO(savedOrder);
    }
}
