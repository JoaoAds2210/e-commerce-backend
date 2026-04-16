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

import java.util.Map;

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

            for (Map.Entry<Long, Integer> entry : event.productQuantities().entrySet()) {
                Long productId = entry.getKey();
                int quantityToDeduct = entry.getValue();

                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException(
                                "Produto não encontrado | productId=" + productId));

                if (product.getStockQuantity() < quantityToDeduct) {
                    log.warn("[Kafka] Estoque insuficiente | productId={} | disponível={} | solicitado={}",
                            productId, product.getStockQuantity(), quantityToDeduct);
                    continue;
                }

                product.setStockQuantity(product.getStockQuantity() - quantityToDeduct);
                productRepository.save(product);

                log.info("[Kafka] Estoque atualizado | productId={} | novoEstoque={}",
                        productId, product.getStockQuantity());
            }

            ack.acknowledge();

        } catch (Exception ex) {
            log.error("[Kafka] Erro ao processar order.created | payload={}", record.value(), ex);
            ack.acknowledge();
        }
    }
}