package com.example.springboot.orderItem.dtos;

import jakarta.validation.constraints.NotNull;

public record OrderItemCreateDTO(

        @NotNull
        Long orderId,

        @NotNull
        Long productId,

        @NotNull
        Integer quantity
) {}
