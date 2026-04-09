package com.example.springboot.product.mapper;

import com.example.springboot.product.dtos.category.CategoryCreateDTO;
import com.example.springboot.product.dtos.category.CategoryResponseDTO;
import com.example.springboot.product.entity.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryCreateDTO dto){
        return Category.builder()
                .name(dto.name())
                .description(dto.description())
                .build();
    }

    public static CategoryResponseDTO toDTO(Category category){
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
