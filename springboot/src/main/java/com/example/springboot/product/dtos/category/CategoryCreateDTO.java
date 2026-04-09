package com.example.springboot.product.dtos.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreateDTO(

        @NotBlank
        String name,

        @NotBlank
        @Size(min = 3, max = 100)
        String description

) {}
