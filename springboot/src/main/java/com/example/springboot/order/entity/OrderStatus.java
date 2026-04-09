package com.example.springboot.order.entity;

public enum OrderStatus {
    CREATED,         // Pedido criado (antes de pagamento)
    PENDING_PAYMENT,// Aguardando pagamento
    PAID,           // Pagamento aprovado
    PROCESSING,     // Separando / preparando pedido
    SHIPPED,        // Enviado
    DELIVERED,      // Entregue
    CANCELLED,      // Cancelado
    REFUNDED,       // Reembolsado
    PENDING
}
