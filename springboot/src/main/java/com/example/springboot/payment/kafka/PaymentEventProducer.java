package com.example.springboot.payment.kafka;


import com.example.springboot.kafka.events.PaymentConfirmedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j //O @Slf4j gera automaticamente um atributo log na classe
@Component
@RequiredArgsConstructor
public class PaymentEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    // O KafkaTemplate é a classe do Spring que abstrai toda a comunicação com o broker.
    // Os generics definem os tipos da key e do value de cada mensagem.
    public void sendPaymentConfirmed(PaymentConfirmedEvent event) {
        String key = "orderId:" + event.orderId();
        // A key determina em qual partição a mensagem vai cair
        // Mensagens com a mesma key sempre vão para a mesma partição, e dentro de uma partição as mensagens são estritamente ordenadas.
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send("payment.confirmed", key, event);
        // O CompletableFuture te dá acesso ao resultado quando ele chegar.
        // o envio é assíncrono. Quando você chama .send(), o Spring não espera o broker confirmar o recebimento
        // — ele coloca a mensagem num buffer interno e retorna imediatamente. O broker confirma em background.
        future.whenComplete((result, ex) -> {
            //O .whenComplete() registra um callback que vai ser chamado em dois casos
            if (ex != null) {
                log.error("[Kafka] Falha ao publicar payment.confirmed | orderId={} | erro={}",
                        event.orderId(), ex.getMessage());
            } else {
                log.info("[Kafka] payment.confirmed publicado | orderId={} | partition={} | offset={}",
                        event.orderId(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });
    }
}
