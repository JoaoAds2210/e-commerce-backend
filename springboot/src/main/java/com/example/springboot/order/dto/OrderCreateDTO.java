package com.example.springboot.order.dto;

import com.example.springboot.orderItem.dtos.OrderItemCreateDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderCreateDTO (
        @NotNull
        Long userId,

        @NotNull
        Long addressId,

        @NotNull
        List<OrderItemCreateDTO> items
){}
