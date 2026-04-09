package com.example.springboot.orderItem.services;

import com.example.springboot.orderItem.dtos.OrderItemCreateDTO;
import com.example.springboot.orderItem.dtos.OrderItemResponseDTO;
import com.example.springboot.orderItem.dtos.OrderItemUpdateDTO;

import java.util.List;

public interface OrderItemService {

    List<OrderItemResponseDTO> findAll();

    List<OrderItemResponseDTO> findByOrderId(Long orderId);

    OrderItemResponseDTO findById(Long id);

    OrderItemResponseDTO create(OrderItemCreateDTO dto);

    OrderItemResponseDTO update(OrderItemUpdateDTO dto);

    void delete(Long id);

}
