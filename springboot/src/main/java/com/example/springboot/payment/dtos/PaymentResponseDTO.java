package com.example.springboot.payment.dtos;

import com.example.springboot.payment.entity.PaymentMethod;
import com.example.springboot.payment.entity.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseDTO (
        Long id,
        PaymentStatus status,
        PaymentMethod method,
        BigDecimal amount,
        LocalDateTime createdAt,
        LocalDateTime paidAt
) {}
