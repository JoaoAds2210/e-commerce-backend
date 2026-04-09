package com.example.springboot.product.service.category;

import com.example.springboot.product.dtos.category.CategoryCreateDTO;
import com.example.springboot.product.dtos.category.CategoryResponseDTO;
import com.example.springboot.product.dtos.category.CategoryUpdateDTO;
import com.example.springboot.product.entity.Category;
import com.example.springboot.product.mapper.CategoryMapper;
import com.example.springboot.product.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServicesImp implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDTO)
                .toList();
    }

    @Override
    public CategoryResponseDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found by id"));

        return CategoryMapper.toDTO(category);
    }

    @Override
    public CategoryResponseDTO findByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Category not found by name"));

        return CategoryMapper.toDTO(category);
    }

    @Override
    public CategoryResponseDTO create(CategoryCreateDTO dto) {

        if (categoryRepository.findByName(dto.name()).isPresent()){
            throw new RuntimeException("Category exists by name");
        }

        Category category = CategoryMapper.toEntity(dto);

        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryUpdateDTO dto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found by id"));

        if (dto.name() != null && !dto.name().isBlank()){
            category.setName(dto.name().trim());
        }

        if (dto.description() != null && !dto.description().isBlank()){
            category.setDescription(dto.description().trim());
        }

        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Category not found by id"));

        categoryRepository.deleteById(id);
    }
}
