package com.example.springboot.address.controller;

import com.example.springboot.address.dtos.AddressCreateDTO;
import com.example.springboot.address.dtos.AddressResponseDTO;
import com.example.springboot.address.dtos.AddressUpdateDTO;
import com.example.springboot.address.services.AddressServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Addresses", description = "Addresses Management")
@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressServiceImp addressService;

    @Operation(summary = "List All")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List returned with success"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> findAll(){
        return ResponseEntity.ok(addressService.findAll());
    }

    @Operation(summary = "Search address by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address founded"),
            @ApiResponse(responseCode = "404", description = "Address not founded")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(addressService.findById(id));
    }

    @Operation(summary = "Search Address bu User")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address founded by User Id"),
            @ApiResponse(responseCode = "404", description = "Address not founded by User Id")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponseDTO>> findByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(addressService.findByUserId(userId));
    }

    @Operation(summary = "Create address")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PostMapping
    public ResponseEntity<AddressResponseDTO> create(@RequestBody @Valid AddressCreateDTO dto){
        return ResponseEntity.status(201).body(addressService.create(dto));
    }

    @Operation(summary = "Update Address")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated"),
            @ApiResponse(responseCode = "404", description = "Address not founded")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> update(@PathVariable Long id,
                                                     @RequestBody @Valid AddressUpdateDTO dto){
        return ResponseEntity.ok(addressService.update(id, dto));
    }

    @Operation(summary = "Delete Address")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deleted"),
            @ApiResponse(responseCode = "404", description = "Address not founded")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
