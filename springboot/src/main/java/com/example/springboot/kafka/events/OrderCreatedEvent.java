package com.example.springboot.kafka.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderCreatedEvent(
        Long orderId,
        Long userId,
        BigDecimal totalAmount,
        List<Long> productIds,
        LocalDateTime createdAt
) {}
