package com.example.springboot.order.dto;

import com.example.springboot.address.entity.Address;

import com.example.springboot.order.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderUpdateDTO (
        @NotNull
        OrderStatus status

) {}
