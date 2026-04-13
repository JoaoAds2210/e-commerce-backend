package com.example.springboot.product.controller;

import com.example.springboot.product.dtos.category.CategoryCreateDTO;
import com.example.springboot.product.dtos.category.CategoryResponseDTO;
import com.example.springboot.product.dtos.category.CategoryUpdateDTO;
import com.example.springboot.product.service.category.CategoryServicesImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Categories", description = "Categories Management")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServicesImp categoryServices;

    @Operation(summary = "List all categories")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List returned with success"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findAll(){

        return ResponseEntity.ok(categoryServices.findAll());
    }

    @Operation(summary = "Search by category ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category founded"),
            @ApiResponse(responseCode = "404", description = "Category not founded")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(categoryServices.findById(id));
    }

    @Operation(summary = "Search by category name")
    @GetMapping("/search/{name}")
    public ResponseEntity<CategoryResponseDTO> findByName(@PathVariable String name){
        return ResponseEntity.ok(categoryServices.findByName(name));
    }

    @Operation(summary = "Create Category")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create (@RequestBody @Valid CategoryCreateDTO dto){
        return ResponseEntity.status(201).body(categoryServices.create(dto));
    }

    @Operation(summary = "Update category")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Updated"),
            @ApiResponse(responseCode = "400", description = "Category not founded by ID")
    })
    @PutMapping ("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long id, @RequestBody @Valid CategoryUpdateDTO dto){
        return ResponseEntity.ok(categoryServices.update(id, dto));
    }

    @Operation(summary = "Delete Category")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Deleted"),
            @ApiResponse(responseCode = "400", description = "Category not founded by ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        categoryServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
