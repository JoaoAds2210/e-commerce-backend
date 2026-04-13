package com.example.springboot.order.repository;

import com.example.springboot.order.entity.Order;
import com.fasterxml.jackson.annotation.OptBoolean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdWithItem(Long id);

    List<Order> findByUserId(Long userId);

    List<Order> findByAddressId(Long addressId);

    List<Order> findAllWithItems();


}
