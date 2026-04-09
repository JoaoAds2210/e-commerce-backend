package com.example.springboot.order.services;

import com.example.springboot.order.dto.OrderCreateDTO;
import com.example.springboot.order.dto.OrderResponseDTO;
import com.example.springboot.order.dto.OrderUpdateDTO;
import com.example.springboot.order.entity.OrderStatus;

import java.util.List;

public interface OrderService {

    List<OrderResponseDTO> findAllOrders();

    OrderResponseDTO findByIdOrder(Long id);

    List<OrderResponseDTO> findByUserId(Long userId);

    List<OrderResponseDTO> findByAddressId(Long addressId);

    OrderResponseDTO createOrder(OrderCreateDTO dto);

    OrderResponseDTO updateStatusOrder(Long id, OrderUpdateDTO dto);

    void cancelOrder(Long id);
}