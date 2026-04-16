package com.example.springboot.payment.repository;

import com.example.springboot.order.entity.Order;
import com.example.springboot.payment.entity.Payment;
import com.example.springboot.payment.entity.PaymentMethod;
import com.example.springboot.payment.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByMethod(PaymentMethod method);

    List<Payment> findByStatus(PaymentStatus status);

    boolean existsByOrder(Order order);
}
