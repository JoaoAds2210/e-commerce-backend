package com.example.springboot.address.controller;

import com.example.springboot.address.dtos.AddressCreateDTO;
import com.example.springboot.address.dtos.AddressResponseDTO;
import com.example.springboot.address.dtos.AddressUpdateDTO;
import com.example.springboot.address.services.AddressServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressServiceImp addressService;

    //GET
    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> findAll(){
        List<AddressResponseDTO> address = addressService.findAll();

        return ResponseEntity.ok(address);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> findById(@PathVariable Long id){

        AddressResponseDTO address = addressService.findById(id);

        return ResponseEntity.ok(address);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponseDTO>> findByUserId(@PathVariable Long userId){

        List<AddressResponseDTO> address = addressService.findByUserId(userId);

        return ResponseEntity.ok(address);
    }
    //POST
    @PostMapping
    public ResponseEntity<AddressResponseDTO> create (@RequestBody @Valid AddressCreateDTO dto){

        AddressResponseDTO addressCreate = addressService.create(dto);

        return ResponseEntity.status(201).body(addressCreate);
    }
    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> update(@PathVariable Long id, @RequestBody @Valid AddressUpdateDTO dto){

        AddressResponseDTO addressUpdate = addressService.update(id, dto);

        return ResponseEntity.ok(addressUpdate);
    }
    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        addressService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
