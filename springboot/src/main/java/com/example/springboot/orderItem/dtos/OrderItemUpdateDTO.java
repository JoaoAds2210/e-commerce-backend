package com.example.springboot.orderItem.dtos;

import jakarta.validation.constraints.NotNull;

public record OrderItemUpdateDTO(

        @NotNull
        Long id,

        @NotNull
        Integer quantity
) {}
