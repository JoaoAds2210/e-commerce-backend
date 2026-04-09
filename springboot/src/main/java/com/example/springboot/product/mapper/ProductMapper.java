package com.example.springboot.product.mapper;

import com.example.springboot.product.dtos.product.ProductCreateDTO;
import com.example.springboot.product.dtos.product.ProductResponseDTO;
import com.example.springboot.product.entity.Category;
import com.example.springboot.product.entity.Product;

import java.time.LocalDateTime;

public class ProductMapper {

    public static Product toEntity(ProductCreateDTO dto, Category category){
        return Product.builder()
                .name(dto.name())
                .price(dto.price())
                .stockQuantity(dto.stockQuantity())
                .description(dto.description())
                .active(true)
                .category(category)
                .build();
    }

    public static ProductResponseDTO toDTO(Product product){
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getDescription(),
                CategoryMapper.toDTO(product.getCategory()),
                product.getActive(),
                product.getCreatedAt()
        );
    }
}
