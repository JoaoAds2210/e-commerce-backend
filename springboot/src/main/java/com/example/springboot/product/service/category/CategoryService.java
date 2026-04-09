package com.example.springboot.product.service.category;

import com.example.springboot.product.dtos.category.CategoryCreateDTO;
import com.example.springboot.product.dtos.category.CategoryResponseDTO;
import com.example.springboot.product.dtos.category.CategoryUpdateDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDTO> findAll();

    CategoryResponseDTO findById(Long id);

    CategoryResponseDTO findByName(String name);

    CategoryResponseDTO create(CategoryCreateDTO dto);

    CategoryResponseDTO update(Long id, CategoryUpdateDTO dto);

    void delete(Long id);
}
