package com.example.springboot.product.dtos.product;

import com.example.springboot.product.dtos.category.CategoryResponseDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDTO(
        Long id,
        String name,
        BigDecimal price,
        Integer stockQuantity,
        String description,
        CategoryResponseDTO category,
        Boolean active,
        LocalDateTime createdAt
) {}
