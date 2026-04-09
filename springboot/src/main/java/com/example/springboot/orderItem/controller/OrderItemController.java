package com.example.springboot.orderItem.controller;

import com.example.springboot.orderItem.dtos.OrderItemCreateDTO;
import com.example.springboot.orderItem.dtos.OrderItemResponseDTO;
import com.example.springboot.orderItem.dtos.OrderItemUpdateDTO;
import com.example.springboot.orderItem.entity.OrderItem;
import com.example.springboot.orderItem.mapper.OrderItemMapper;
import com.example.springboot.orderItem.services.OrderItemService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderItems")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    //GET
    @GetMapping
    public ResponseEntity<List<OrderItemResponseDTO>> findAll(){
        List<OrderItemResponseDTO> orderItem = orderItemService.findAll();

        return ResponseEntity.ok(orderItem);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItemResponseDTO>> findByOrderId(@PathVariable Long orderId){
        List<OrderItemResponseDTO> orderItem = orderItemService.findByOrderId(orderId);

        return ResponseEntity.ok(orderItem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponseDTO> findById(@PathVariable Long id){
        OrderItemResponseDTO orderItem = orderItemService.findById(id);

        return ResponseEntity.ok(orderItem);
    }

    //POST
    @PostMapping
    public ResponseEntity<OrderItemResponseDTO> create (@RequestBody @Valid OrderItemCreateDTO dto){
        OrderItemResponseDTO orderItem = orderItemService.create(dto);

        return ResponseEntity.status(201).body(orderItem);
    }

    //PUT
    @PatchMapping
    public ResponseEntity<OrderItemResponseDTO> update(@RequestBody @Valid OrderItemUpdateDTO dto){
        OrderItemResponseDTO order = orderItemService.update(dto);

        return ResponseEntity.ok(order);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        orderItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
