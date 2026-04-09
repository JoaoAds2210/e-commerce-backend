package com.example.springboot.payment.controller;

import com.example.springboot.payment.dtos.PaymentCreateDTO;
import com.example.springboot.payment.dtos.PaymentResponseDTO;
import com.example.springboot.payment.dtos.PaymentUpdateDTO;
import com.example.springboot.payment.entity.PaymentMethod;
import com.example.springboot.payment.entity.PaymentStatus;
import com.example.springboot.payment.services.PaymentServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentServices paymentServices;

    //GET
    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> findAll(){
        List<PaymentResponseDTO> findAll = paymentServices.findAll();

        return ResponseEntity.ok(findAll);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> findById(@PathVariable Long id){
        PaymentResponseDTO payment = paymentServices.findById(id);

        return ResponseEntity.ok(payment);
    }

    @GetMapping("/method/{method}")
    public ResponseEntity<List<PaymentResponseDTO>> findByMethodPayment(@PathVariable PaymentMethod method){
        List<PaymentResponseDTO> methodPayment = paymentServices.findByMethodPayment(method);

        return ResponseEntity.ok(methodPayment);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentResponseDTO>> findByStatusPayment(@PathVariable PaymentStatus status){
        List<PaymentResponseDTO> statusPayment = paymentServices.findByStatusPayment(status);

        return ResponseEntity.ok(statusPayment);
    }

    //POST
    @PostMapping
    public ResponseEntity<PaymentResponseDTO> create (@RequestBody @Valid PaymentCreateDTO dto){
        PaymentResponseDTO createPayment = paymentServices.create(dto);

        return ResponseEntity.status(201).body(createPayment);
    }

    //PUT/PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> update (@PathVariable Long id, @RequestBody @Valid PaymentUpdateDTO dto){
        PaymentResponseDTO updatePayment = paymentServices.update(id, dto);

        return ResponseEntity.ok(updatePayment);
    }
    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        paymentServices.delete(id);

        return ResponseEntity.noContent().build();
    }
}
