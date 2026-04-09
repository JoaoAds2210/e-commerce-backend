package com.example.springboot.product.service.product;

import com.example.springboot.product.dtos.product.ProductCreateDTO;
import com.example.springboot.product.dtos.product.ProductResponseDTO;
import com.example.springboot.product.dtos.product.ProductUpdateDTO;
import com.example.springboot.product.entity.Category;


import java.util.List;

public interface ProductServices {

    List<ProductResponseDTO> findAll();

    ProductResponseDTO findById(Long id);

    ProductResponseDTO findByName (String name);

    List<ProductResponseDTO> findByCategoryId(Long categoryId);

    public ProductResponseDTO create(ProductCreateDTO dto);

    public ProductResponseDTO update (Long id, ProductUpdateDTO dto);

    void delete (Long id);
}
