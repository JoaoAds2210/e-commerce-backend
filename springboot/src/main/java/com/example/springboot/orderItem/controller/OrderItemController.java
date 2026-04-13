package com.example.springboot.orderItem.controller;

import com.example.springboot.orderItem.dtos.OrderItemCreateDTO;
import com.example.springboot.orderItem.dtos.OrderItemResponseDTO;
import com.example.springboot.orderItem.dtos.OrderItemUpdateDTO;
import com.example.springboot.orderItem.entity.OrderItem;
import com.example.springboot.orderItem.mapper.OrderItemMapper;
import com.example.springboot.orderItem.services.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Items Orders", description = "Order item management")
@RestController
@RequestMapping("/orderItems")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @Operation(summary = "List all items")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List returned with success"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<OrderItemResponseDTO>> findAll(){
        return ResponseEntity.ok(orderItemService.findAll());
    }

    @Operation(summary = "Search Order by Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order founded"),
            @ApiResponse(responseCode = "404", description = "Order not founded")
    })
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItemResponseDTO>> findByOrderId(@PathVariable Long orderId){
        return ResponseEntity.ok(orderItemService.findByOrderId(orderId));
    }

    @Operation(summary = "Search Item by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item founded"),
            @ApiResponse(responseCode = "404", description = "Item not founded")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(orderItemService.findById(id));
    }

    @Operation(summary = "Criar item do pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PostMapping
    public ResponseEntity<OrderItemResponseDTO> create (@RequestBody @Valid OrderItemCreateDTO dto){
        return ResponseEntity.status(201).body(orderItemService.create(dto));
    }

    @Operation(summary = "Update item by order")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Updated"),
            @ApiResponse(responseCode = "400", description = "Item not founded by ID")
    })
    @PatchMapping
    public ResponseEntity<OrderItemResponseDTO> update(@RequestBody @Valid OrderItemUpdateDTO dto){
        return ResponseEntity.ok(orderItemService.update(dto));
    }

    @Operation(summary = "Detele item")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Deleted"),
            @ApiResponse(responseCode = "400", description = "Item not founded by ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        orderItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

