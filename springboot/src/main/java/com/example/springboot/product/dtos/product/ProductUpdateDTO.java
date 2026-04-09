package com.example.springboot.product.dtos.product;

import com.example.springboot.product.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record ProductUpdateDTO(
        @NotBlank
        String name,

        @NotNull
        BigDecimal price,

        @NotNull
        Integer stockQuantity,

        @NotBlank
        String description,

        @NotNull
        Boolean active,

        @NotNull
        Long categoryId
) {}
