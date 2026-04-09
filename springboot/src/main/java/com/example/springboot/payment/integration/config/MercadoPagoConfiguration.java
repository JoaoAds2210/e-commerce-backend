package com.example.springboot.payment.integration.config;

import com.mercadopago.MercadoPagoConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

//Isso é uma classe do SDK oficial do Mercado Pago,
//Ela funciona como um configurador global do SDK.
@Configuration //inicializar e preparar dependências externas quando a aplicação sobe.
public class MercadoPagoConfiguration {

    @Value("${mercadopago.access.token}")
    private String accessToken;

    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken(accessToken);
    }
}
