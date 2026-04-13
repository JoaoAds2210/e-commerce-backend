package com.example.springboot.order.controller;

import com.example.springboot.order.dto.OrderCreateDTO;
import com.example.springboot.order.dto.OrderResponseDTO;
import com.example.springboot.order.dto.OrderUpdateDTO;
import com.example.springboot.order.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Orders", description = "Orders Management")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Find all orders")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List returned with success"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> findAllOrders(){
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @Operation(summary = "Search Order by Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order founded"),
            @ApiResponse(responseCode = "404", description = "Order not founded")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findByIdOrder(@PathVariable Long id){
        return ResponseEntity.ok(orderService.findByIdOrder(id));
    }

    @Operation(summary = "Search Order by User ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order founded by user"),
            @ApiResponse(responseCode = "404", description = "Order not founded by user")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> findByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(orderService.findByUserId(userId));
    }

    @Operation(summary = "Search Order by Address ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order founded by address"),
            @ApiResponse(responseCode = "404", description = "Order not founded by address")
    })
    @GetMapping("/address/{addressId}")
    public ResponseEntity<List<OrderResponseDTO>> findByAddressId(@PathVariable Long addressId){
        return ResponseEntity.ok(orderService.findByAddressId(addressId));
    }

    @Operation(summary = "Create Order")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created with success"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid OrderCreateDTO dto){
        return ResponseEntity.status(201).body(orderService.createOrder(dto));
    }

    @Operation(summary = "Update Order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated with success"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(@PathVariable Long id, @RequestBody @Valid OrderUpdateDTO dto) {
        return ResponseEntity.ok(orderService.updateStatusOrder(id, dto));
    }

    @Operation(summary = "Delete Order")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deleted with success"),
            @ApiResponse(responseCode = "404", description = "Order not founded")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id){
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }
}
