package com.example.springboot.order.kafka;

import com.example.springboot.kafka.events.PaymentConfirmedEvent;
import com.example.springboot.order.entity.Order;
import com.example.springboot.order.entity.OrderStatus;
import com.example.springboot.order.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderStatusConsumer {

    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "payment.confirmed",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handlePaymentConfirmed(
            ConsumerRecord<String, String> record,
            Acknowledgment ack
    ) {

        log.info("[Kafka] payment.confirmed recebido | partition={} | offset={} | key={}",
                record.partition(), record.offset(), record.key());

        try {
            PaymentConfirmedEvent event =
                    objectMapper.readValue(record.value(), PaymentConfirmedEvent.class);

            Order order = orderRepository.findById(event.orderId())
                    .orElseThrow(() -> new RuntimeException(
                            "Order não encontrada | orderId=" + event.orderId()));

            if (order.getStatus() == OrderStatus.PAID) {
                log.warn("[Kafka] Order já está PAID | orderId={}", event.orderId());
                ack.acknowledge();
                return;
            }

            order.setStatus(OrderStatus.PAID);
            orderRepository.save(order);

            ack.acknowledge();

        } catch (Exception ex) {
            log.error("[Kafka] Erro ao processar payment.confirmed | payload={}", record.value(), ex);

            ack.acknowledge(); // evita loop infinito
        }
    }
}