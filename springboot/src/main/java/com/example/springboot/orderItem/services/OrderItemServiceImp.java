package com.example.springboot.orderItem.services;


import com.example.springboot.order.entity.Order;
import com.example.springboot.order.repository.OrderRepository;
import com.example.springboot.orderItem.dtos.OrderItemCreateDTO;
import com.example.springboot.orderItem.dtos.OrderItemResponseDTO;
import com.example.springboot.orderItem.dtos.OrderItemUpdateDTO;
import com.example.springboot.orderItem.entity.OrderItem;
import com.example.springboot.orderItem.mapper.OrderItemMapper;
import com.example.springboot.orderItem.repository.OrderItemRepository;
import com.example.springboot.product.entity.Product;
import com.example.springboot.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImp implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<OrderItemResponseDTO> findAll() {
        return orderItemRepository.findAll()
                .stream()
                .map(OrderItemMapper::toDTO)
                .toList();
    }

    @Override
    public List<OrderItemResponseDTO> findByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId)
                .stream()
                .map(OrderItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemResponseDTO findById(Long id) {

        OrderItem item = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found by id"));

        return OrderItemMapper.toDTO(item);
    }


    @Override
    public OrderItemResponseDTO create(OrderItemCreateDTO dto) {

        Order order = orderRepository.findById(dto.orderId())
                .orElseThrow(() -> new RuntimeException("Order not found by ID"));

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new RuntimeException("Product not foud by ID"));

        if (product.getStockQuantity() < dto.quantity()){
            throw new RuntimeException("Produto insuficiente no momento");
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(dto.quantity());
        orderItem.setPrice(product.getPrice());

        product.setStockQuantity(product.getStockQuantity() - dto.quantity());
        productRepository.save(product);

        OrderItem savedItem = orderItemRepository.save(orderItem);

        return OrderItemMapper.toDTO(savedItem);
    }

    @Override
    public OrderItemResponseDTO update(OrderItemUpdateDTO dto) {

        OrderItem orderItem = orderItemRepository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("OrderItem não encontrado"));

        Product product = orderItem.getProduct();

        int oldQuantity = orderItem.getQuantity();
        int newQuantity = dto.quantity();
        int difference = newQuantity - oldQuantity;

        if (difference > 0 && product.getStockQuantity() < difference) {
            throw new RuntimeException("Estoque insuficiente para aumentar a quantidade do produto: " + product.getName());
        }

        product.setStockQuantity(product.getStockQuantity() - difference);
        productRepository.save(product);

        orderItem.setQuantity(newQuantity);
        OrderItem updatedItem = orderItemRepository.save(orderItem);

        return OrderItemMapper.toDTO(updatedItem);
    }

    @Override
    public void delete(Long id) {
        orderItemRepository.deleteById(id);
    }
}
