package com.example.springboot.order.services;

import com.example.springboot.address.entity.Address;
import com.example.springboot.address.repository.AddressRepository;
import com.example.springboot.order.dto.OrderCreateDTO;
import com.example.springboot.order.dto.OrderResponseDTO;
import com.example.springboot.order.dto.OrderUpdateDTO;
import com.example.springboot.order.entity.Order;
import com.example.springboot.order.entity.OrderStatus;
import com.example.springboot.order.mapper.OrderMapper;
import com.example.springboot.order.repository.OrderRepository;
import com.example.springboot.orderItem.dtos.OrderItemCreateDTO;
import com.example.springboot.orderItem.entity.OrderItem;
import com.example.springboot.product.entity.Product;
import com.example.springboot.product.repository.ProductRepository;
import com.example.springboot.user.repository.UserRepository;
import com.example.springboot.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<OrderResponseDTO> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toDTO)
                .toList();
    }

    @Override
    public OrderResponseDTO findByIdOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return OrderMapper.toDTO(order);
    }

    @Override
    public List<OrderResponseDTO> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderMapper::toDTO)
                .toList();
    }

    @Override
    public List<OrderResponseDTO> findByAddressId(Long addressId) {
        return orderRepository.findByAddressId(addressId)
                .stream()
                .map(OrderMapper::toDTO)
                .toList();
    }

    @Override
    public OrderResponseDTO createOrder(OrderCreateDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(dto.addressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Address does not belong to user");
        }

        Order order = Order.builder()
                .user(user)
                .address(address)
                .status(OrderStatus.PENDING)
                .build();

        List<OrderItem> items = new ArrayList<>();

        for (OrderItemCreateDTO itemDTO : dto.items()) {
            Product product = productRepository.findById(itemDTO.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemDTO.quantity())
                    .price(product.getPrice())
                    .build();

            items.add(item);
        }

        order.setItems(items);

        BigDecimal total = items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(total);

        orderRepository.save(order);

        return OrderMapper.toDTO(order);
    }

    @Override
    public OrderResponseDTO updateStatusOrder(Long id, OrderUpdateDTO dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Pedido cancelado não pode ser alterado");
        }

        order.setStatus(dto.status());
        orderRepository.save(order);

        return OrderMapper.toDTO(order);
    }

    @Override
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only pending orders can be cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);
    }
}
