package com.example.springboot.order.mapper;

import com.example.springboot.address.entity.Address;
import com.example.springboot.order.dto.OrderCreateDTO;
import com.example.springboot.order.dto.OrderResponseDTO;
import com.example.springboot.order.entity.Order;
import com.example.springboot.orderItem.mapper.OrderItemMapper;
import com.example.springboot.user.entity.User;
import com.example.springboot.user.mapper.UserMapper;

public class OrderMapper {

    public static Order toEntity(OrderCreateDTO dto, User user, Address address){
        return Order.builder()
                .user(user)
                .address(address)
                .build();
    }

    public static OrderResponseDTO toDTO(Order order){
        return new OrderResponseDTO(
                order.getId(),
                UserMapper.toDTO(order.getUser()),
                order.getItems().stream()
                        .map(OrderItemMapper::toDTO)
                        .toList(),
                order.getTotalAmount(),
                order.getStatus()
        );
    }
}
