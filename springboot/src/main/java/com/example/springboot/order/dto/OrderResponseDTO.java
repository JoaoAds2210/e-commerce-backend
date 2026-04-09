package com.example.springboot.order.dto;

import com.example.springboot.order.entity.OrderStatus;
import com.example.springboot.orderItem.dtos.OrderItemResponseDTO;
import com.example.springboot.user.dtos.UserResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDTO (

        Long id,
        UserResponseDTO user,
        List<OrderItemResponseDTO> items,
        BigDecimal totalAmount,
        OrderStatus status
) {}
