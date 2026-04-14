package com.example.springboot.kafka.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentConfirmedEvent(
        Long paymentId,
        Long orderId,
        BigDecimal amount,
        LocalDateTime paidAt
) {}

//A regra é: coloca no event apenas o que o consumer precisa para fazer o trabalho dele, sem precisar buscar nada no banco.
