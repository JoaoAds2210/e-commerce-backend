package com.example.springboot.product.controller;

import com.example.springboot.product.dtos.category.CategoryCreateDTO;
import com.example.springboot.product.dtos.category.CategoryResponseDTO;
import com.example.springboot.product.dtos.category.CategoryUpdateDTO;
import com.example.springboot.product.service.category.CategoryServicesImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServicesImp categoryServices;

    //GET
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findAll(){
        List<CategoryResponseDTO> category = categoryServices.findAll();
        return ResponseEntity.ok(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long id){
        CategoryResponseDTO category = categoryServices.findById(id);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<CategoryResponseDTO> findByName(@PathVariable String name){
        CategoryResponseDTO category = categoryServices.findByName(name);
        return ResponseEntity.ok(category);
    }
    //POST
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create (@RequestBody @Valid CategoryCreateDTO dto){
        CategoryResponseDTO category = categoryServices.create(dto);
        return ResponseEntity.status(201).body(category);
    }
    //PUT/PATCH
    @PutMapping ("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long id, @RequestBody @Valid CategoryUpdateDTO dto){
        CategoryResponseDTO category = categoryServices.update(id, dto);
        return ResponseEntity.ok(category);
    }
    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        categoryServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
