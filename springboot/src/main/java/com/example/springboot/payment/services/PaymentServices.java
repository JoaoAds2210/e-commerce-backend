package com.example.springboot.payment.services;

import com.example.springboot.payment.dtos.PaymentCreateDTO;
import com.example.springboot.payment.dtos.PaymentResponseDTO;
import com.example.springboot.payment.dtos.PaymentUpdateDTO;
import com.example.springboot.payment.entity.PaymentMethod;
import com.example.springboot.payment.entity.PaymentStatus;

import java.util.List;

public interface PaymentServices {

    List<PaymentResponseDTO> findAll();

    PaymentResponseDTO findById(Long id);

    List<PaymentResponseDTO> findByMethodPayment(PaymentMethod method);

    List<PaymentResponseDTO> findByStatusPayment(PaymentStatus status);

    PaymentResponseDTO create (PaymentCreateDTO dto);

    PaymentResponseDTO update (Long id, PaymentUpdateDTO dto);

    void delete (Long id);
}
