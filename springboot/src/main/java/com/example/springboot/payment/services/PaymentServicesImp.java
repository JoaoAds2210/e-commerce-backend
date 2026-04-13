package com.example.springboot.payment.services;

import com.example.springboot.order.entity.Order;
import com.example.springboot.order.entity.OrderStatus;
import com.example.springboot.order.repository.OrderRepository;
import com.example.springboot.payment.dtos.PaymentCreateDTO;
import com.example.springboot.payment.dtos.PaymentResponseDTO;
import com.example.springboot.payment.dtos.PaymentUpdateDTO;
import com.example.springboot.payment.entity.Payment;
import com.example.springboot.payment.entity.PaymentMethod;
import com.example.springboot.payment.entity.PaymentStatus;
import com.example.springboot.payment.mapper.PaymentMapper;
import com.example.springboot.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentServicesImp implements PaymentServices {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<PaymentResponseDTO> findAll() {
        return paymentRepository.findAll()
                .stream()
                .map(PaymentMapper::toDTO)
                .toList();
    }

    @Override
    public PaymentResponseDTO findById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found by ID"));

        return PaymentMapper.toDTO(payment);
    }

    @Override
    public List<PaymentResponseDTO> findByMethodPayment(PaymentMethod method) {
        return paymentRepository.findByMethodPayment(method)
                .stream()
                .map(PaymentMapper::toDTO)
                .toList();
    }

    @Override
    public List<PaymentResponseDTO> findByStatusPayment(PaymentStatus status) {
        return paymentRepository.findByStatusPayment(status)
                .stream()
                .map(PaymentMapper::toDTO)
                .toList();
    }

    @Override
    public PaymentResponseDTO create(PaymentCreateDTO dto) {

        Order order = orderRepository.findById(dto.orderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid order amount");
        }

        if (order.getStatus() == OrderStatus.PAID) {
            throw new RuntimeException("Order already paid");
        }

        if (paymentRepository.existsByOrder(order)) {
            throw new RuntimeException("Payment already exists");
        }

        Payment payment = PaymentMapper.toEntity(dto, order);

        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setStatus(PaymentStatus.PENDING);

        switch (dto.method()) {
            case PIX -> {
                payment.setPaymentUrl("pix://qrcode-" + UUID.randomUUID());
                payment.setTransactionId(UUID.randomUUID().toString());
            }
            case CREDIT_CARD -> {
                payment.setTransactionId(UUID.randomUUID().toString());
                payment.setInstallments(1);
            }
            case BOLETO -> {
                payment.setPaymentUrl("boleto://codigo-" + UUID.randomUUID());
                payment.setTransactionId(UUID.randomUUID().toString());
            }
        }

        Payment saved = paymentRepository.save(payment);

        return PaymentMapper.toDTO(saved);
    }

    @Override
    public PaymentResponseDTO update (Long id, PaymentUpdateDTO dto) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getStatus() == PaymentStatus.CANCELLED) {
            throw new RuntimeException("Cancelled payment cannot be updated");
        }

        if (payment.getStatus() == PaymentStatus.PAID) {
            throw new RuntimeException("Payment already completed");
        }

        if (payment.getStatus() == dto.status()) {
            return PaymentMapper.toDTO(payment);
        }

        payment.setStatus(dto.status());

        if (dto.status() == PaymentStatus.PAID) {
            payment.setPaidAt(LocalDateTime.now());

            Order order = payment.getOrder();
            order.setStatus(OrderStatus.PAID);
        }

        Payment updated = paymentRepository.save(payment);

        return PaymentMapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }
}
