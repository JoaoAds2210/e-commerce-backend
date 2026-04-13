package com.example.springboot.payment.controller;

import com.example.springboot.payment.dtos.PaymentCreateDTO;
import com.example.springboot.payment.dtos.PaymentResponseDTO;
import com.example.springboot.payment.dtos.PaymentUpdateDTO;
import com.example.springboot.payment.entity.PaymentMethod;
import com.example.springboot.payment.entity.PaymentStatus;
import com.example.springboot.payment.services.PaymentServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Payments", description = "Payment Management")
@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentServices paymentServices;

    @Operation(summary = "List all payments")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List returned with success"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> findAll(){
        return ResponseEntity.ok(paymentServices.findAll());
    }

    @Operation(summary = "Search payments by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment founded"),
            @ApiResponse(responseCode = "404", description = "Payment not founded")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(paymentServices.findById(id));
    }

    @Operation(summary = "Search by Method Payment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment founded by Methods"),
            @ApiResponse(responseCode = "404", description = "Payment not founded")
    })
    @GetMapping("/method/{method}")
    public ResponseEntity<List<PaymentResponseDTO>> findByMethodPayment(@PathVariable PaymentMethod method){
        return ResponseEntity.ok(paymentServices.findByMethodPayment(method));
    }

    @Operation(summary = "Search by Payment Status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment founded by Status"),
            @ApiResponse(responseCode = "404", description = "Payment not founded")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentResponseDTO>> findByStatusPayment(@PathVariable PaymentStatus status){
        return ResponseEntity.ok(paymentServices.findByStatusPayment(status));
    }

    @Operation(summary = "Create Payment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created"),
            @ApiResponse(responseCode = "404", description = "Invalid data")
    })
    @PostMapping
    public ResponseEntity<PaymentResponseDTO> create (@RequestBody @Valid PaymentCreateDTO dto){
        return ResponseEntity.status(201).body(paymentServices.create(dto));
    }

    @Operation(summary = "Update Payment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated"),
            @ApiResponse(responseCode = "404", description = "Payment not founded")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> update (@PathVariable Long id, @RequestBody @Valid PaymentUpdateDTO dto){
        return ResponseEntity.ok(paymentServices.update(id, dto));
    }

    @Operation(summary = "Delete Payment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleted"),
            @ApiResponse(responseCode = "404", description = "Payment not founded")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        paymentServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
