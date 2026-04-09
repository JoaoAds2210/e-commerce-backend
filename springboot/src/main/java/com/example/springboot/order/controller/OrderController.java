package com.example.springboot.order.controller;

import com.example.springboot.address.repository.AddressRepository;
import com.example.springboot.order.dto.OrderCreateDTO;
import com.example.springboot.order.dto.OrderResponseDTO;

import com.example.springboot.order.dto.OrderUpdateDTO;
import com.example.springboot.order.services.OrderService;

import com.example.springboot.user.dtos.UserResponseDTO;
import com.example.springboot.user.service.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    //GET
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> findAllOrders(){
        List<OrderResponseDTO> orders = orderService.findAllOrders();

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findByIdOrder(@PathVariable Long id){
        OrderResponseDTO orders = orderService.findByIdOrder(id);

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> findByUserId(@PathVariable Long userId){
        List<OrderResponseDTO> orders = orderService.findByUserId(userId);

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<List<OrderResponseDTO>> findByAddressId(@PathVariable Long addressId){
        List<OrderResponseDTO> orders = orderService.findByAddressId(addressId);

        return ResponseEntity.ok(orders);
    }

    //POST
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid OrderCreateDTO dto){

        OrderResponseDTO order = orderService.createOrder(dto);

        return ResponseEntity.status(201).body(order);
    }

    //PUT
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(@PathVariable Long id, @RequestBody @Valid OrderUpdateDTO dto) {
        OrderResponseDTO update = orderService.updateStatusOrder(id, dto);
        return ResponseEntity.ok(update);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id){
        orderService.findByIdOrder(id);

        return ResponseEntity.noContent().build();
    }

}
