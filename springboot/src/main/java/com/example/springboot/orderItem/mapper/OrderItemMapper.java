package com.example.springboot.orderItem.mapper;

import com.example.springboot.orderItem.dtos.OrderItemCreateDTO;
import com.example.springboot.orderItem.dtos.OrderItemResponseDTO;
import com.example.springboot.orderItem.entity.OrderItem;
import com.example.springboot.product.entity.Product;

public class OrderItemMapper {

    public static OrderItem toEntity(OrderItemCreateDTO dto, Product product) {
        return OrderItem.builder()
                .product(product)
                .quantity(dto.quantity())
                .price(product.getPrice())
                .build();
    }

    public static OrderItemResponseDTO toDTO(OrderItem orderItem) {
        return new OrderItemResponseDTO(
                orderItem.getId(),
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getSubtotal()
        );
    }
}