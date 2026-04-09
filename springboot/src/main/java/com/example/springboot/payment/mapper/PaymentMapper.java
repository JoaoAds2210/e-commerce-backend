package com.example.springboot.payment.mapper;

import com.example.springboot.payment.dtos.PaymentCreateDTO;
import com.example.springboot.payment.dtos.PaymentResponseDTO;
import com.example.springboot.payment.entity.Payment;


public class PaymentMapper {

    public static Payment toEntity (PaymentCreateDTO dto){
        return Payment.builder()
                .method(dto.method())
                .payerEmail(dto.payerEmail())
                .payerDocument(dto.payerDocument())
                .build();
    }

    public static PaymentResponseDTO toDTO (Payment payment){
        return new PaymentResponseDTO(
          payment.getId(),
          payment.getStatus(),
          payment.getMethod(),
          payment.getAmount(),
          payment.getCreatedAt(),
          payment.getPaidAt()
        );
    }

}
