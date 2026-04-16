package com.example.springboot.kafka.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public record OrderCreatedEvent(
        Long orderId,
        Long userId,
        BigDecimal totalAmount,
        Map<Long, Integer> productQuantities,
        LocalDateTime createdAt
) {}
