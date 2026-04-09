package com.example.springboot.product.controller;

import com.example.springboot.product.dtos.product.ProductCreateDTO;
import com.example.springboot.product.dtos.product.ProductResponseDTO;
import com.example.springboot.product.dtos.product.ProductUpdateDTO;
import com.example.springboot.product.entity.Category;
import com.example.springboot.product.entity.Product;
import com.example.springboot.product.mapper.ProductMapper;
import com.example.springboot.product.service.product.ProductServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        List<ProductResponseDTO> products = productServices.findAll(); // Service já retorna DTO
        return ResponseEntity.ok(products);
    }

    // GET - por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        ProductResponseDTO product = productServices.findById(id); // Service já retorna DTO
        return ResponseEntity.ok(product);
    }

    // GET - por nome
    @GetMapping("/search/{name}")
    public ResponseEntity<ProductResponseDTO> findByName(@PathVariable String name) {
        ProductResponseDTO product = productServices.findByName(name); // Service já retorna DTO
        return ResponseEntity.ok(product);
    }

    // GET - por categoria
    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductResponseDTO>> findByCategory(@PathVariable Long id) {
        List<ProductResponseDTO> products = productServices.findByCategoryId(id); // Service já retorna DTO
        return ResponseEntity.ok(products);
    }

    // POST - criar produto
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody @Valid ProductCreateDTO dto) {
        ProductResponseDTO createdProduct = productServices.create(dto);
        return ResponseEntity.status(201).body(createdProduct);
    }

    // PUT - atualizar produto
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id,
                                                     @RequestBody @Valid ProductUpdateDTO dto) {
        ProductResponseDTO updatedProduct = productServices.update(id, dto);
        return ResponseEntity.ok(updatedProduct);
    }

    // DELETE - remover produto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
