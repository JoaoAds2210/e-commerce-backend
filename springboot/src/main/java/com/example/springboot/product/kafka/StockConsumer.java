package com.example.springboot.product.kafka;

import com.example.springboot.kafka.events.OrderCreatedEvent;
import com.example.springboot.product.entity.Product;
import com.example.springboot.product.repository.ProductRepository;
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
public class StockConsumer {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "order.created",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleOrderCreated(
            ConsumerRecord<String, String> record,
            Acknowledgment ack
    ) {

        log.info("[Kafka] order.created recebido | partition={} | offset={} | key={}",
                record.partition(), record.offset(), record.key());

        try {
            OrderCreatedEvent event =
                    objectMapper.readValue(record.value(), OrderCreatedEvent.class);

            for (Long productId : event.productIds()) {

                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException(
                                "Produto não encontrado | productId=" + productId));

                if (product.getStockQuantity() <= 0) {
                    log.warn("[Kafka] Produto sem estoque | productId={}", productId);
                    continue;
                }

                product.setStockQuantity(product.getStockQuantity() - 1);
                productRepository.save(product);
            }

            ack.acknowledge(); // ✅ confirma processamento

        } catch (Exception ex) {
            log.error("[Kafka] Erro ao processar order.created | payload={}", record.value(), ex);

            // evita loop infinito em caso de mensagem inválida
            ack.acknowledge();
        }
    }
}