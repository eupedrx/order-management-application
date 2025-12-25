package com.example.testelynx.service;

import com.example.testelynx.domain.Customers;
import com.example.testelynx.domain.OrderItems;
import com.example.testelynx.domain.Orders;
import com.example.testelynx.domain.Products;
import com.example.testelynx.domain.enums.OrderStatus;
import com.example.testelynx.dto.CreateOrdersDTO;
import com.example.testelynx.dto.CreateOrdersItemDTO;
import com.example.testelynx.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}
