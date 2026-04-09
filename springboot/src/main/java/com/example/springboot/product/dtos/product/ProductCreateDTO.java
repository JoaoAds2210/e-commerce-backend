package com.example.springboot.product.dtos.product;

import com.example.springboot.product.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductCreateDTO(

        @NotBlank
        @Size(min = 3, max = 100)
        String name,

        @NotNull
        BigDecimal price,

        @NotNull
        Integer stockQuantity,

        @NotBlank
        String description,

        @NotNull
        Long categoryId

) {}
