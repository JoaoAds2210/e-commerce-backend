package com.example.springboot.payment.dtos;

import com.example.springboot.payment.entity.PaymentStatus;
import jakarta.validation.constraints.NotNull;

public record PaymentUpdateDTO (

        @NotNull
        Long id,

        @NotNull
        PaymentStatus status
) {}
