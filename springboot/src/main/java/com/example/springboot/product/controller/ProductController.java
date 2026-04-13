package com.example.springboot.product.controller;

import com.example.springboot.product.dtos.product.ProductCreateDTO;
import com.example.springboot.product.dtos.product.ProductResponseDTO;
import com.example.springboot.product.dtos.product.ProductUpdateDTO;
import com.example.springboot.product.service.product.ProductServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Products", description = "Products Management")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @Operation(summary = "List all products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List returned with success"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        return ResponseEntity.ok(productServices.findAll());
    }

    @Operation(summary = "Search product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product founded"),
            @ApiResponse(responseCode = "404", description = "Product not founded")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productServices.findById(id));
    }

    @Operation(summary = "Search product by name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product founded"),
            @ApiResponse(responseCode = "404", description = "Product not founded by name")
    })
    @GetMapping("/search/{name}")
    public ResponseEntity<ProductResponseDTO> findByName(@PathVariable String name) {
        return ResponseEntity.ok(productServices.findByName(name));
    }

    @Operation(summary = "Search products by category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product founded"),
            @ApiResponse(responseCode = "404", description = "Product not founded by category")
    })
    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductResponseDTO>> findByCategory(@PathVariable Long id) {
        return ResponseEntity.ok(productServices.findByCategoryId(id));
    }

    @Operation(summary = "Create product")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody @Valid ProductCreateDTO dto) {
        return ResponseEntity.status(201).body(productServices.create(dto));
    }

    @Operation(summary = "Update product")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Updated"),
            @ApiResponse(responseCode = "400", description = "Product not founded by ID")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ProductUpdateDTO dto) {
        return ResponseEntity.ok(productServices.update(id, dto));
    }

    @Operation(summary = "Delete product")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Deleted"),
            @ApiResponse(responseCode = "400", description = "Product not founded by ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
