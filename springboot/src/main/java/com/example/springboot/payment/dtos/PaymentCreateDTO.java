package com.example.springboot.payment.dtos;

import com.example.springboot.payment.entity.PaymentMethod;
import jakarta.validation.constraints.NotNull;

public record PaymentCreateDTO (

        @NotNull
        Long orderId,

        @NotNull
        PaymentMethod method,

        String payerEmail,

        String payerDocument

) {}
