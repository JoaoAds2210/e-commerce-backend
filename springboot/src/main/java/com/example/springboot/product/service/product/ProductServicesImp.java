package com.example.springboot.product.service.product;

import com.example.springboot.product.dtos.product.ProductCreateDTO;
import com.example.springboot.product.dtos.product.ProductResponseDTO;
import com.example.springboot.product.dtos.product.ProductUpdateDTO;
import com.example.springboot.product.entity.Category;
import com.example.springboot.product.entity.Product;
import com.example.springboot.product.mapper.ProductMapper;
import com.example.springboot.product.repository.CategoryRepository;
import com.example.springboot.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServicesImp implements ProductServices{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductResponseDTO> findAll() {

        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com esse ID"));

        return ProductMapper.toDTO(product);
    }

    @Override
    public ProductResponseDTO findByName(String name) {
        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Produto nao encontrado com esse nome"));

        return ProductMapper.toDTO(product);
    }

    @Override
    public List<ProductResponseDTO> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO create(ProductCreateDTO dto) {
        if (productRepository.findByName(dto.name()).isPresent()) {
            throw new RuntimeException("Produto já cadastrado com esse nome");
        }

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        Product product = ProductMapper.toEntity(dto, category);

        Product savedProduct = productRepository.save(product);

        return ProductMapper.toDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO update(Long id, ProductUpdateDTO dto) {
        Product oldProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (dto.name() != null && !dto.name().isBlank()) {
            oldProduct.setName(dto.name());
        }

        if (dto.price() != null) {
            if (dto.price().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("O preço deve ser maior que 0");
            }
            oldProduct.setPrice(dto.price());
        }

        if (dto.stockQuantity() != null) {
            if (dto.stockQuantity() < 0) {
                throw new RuntimeException("A quantidade em estoque não pode ser negativa");
            }
            oldProduct.setStockQuantity(dto.stockQuantity());
        }

        if (dto.description() != null && !dto.description().isBlank()) {
            oldProduct.setDescription(dto.description());
        }

        if (dto.active() != null) {
            oldProduct.setActive(dto.active());
        }

        if (dto.categoryId() != null) {
            Category category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            oldProduct.setCategory(category);
        }

        Product savedProduct = productRepository.save(oldProduct);
        return ProductMapper.toDTO(savedProduct);
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.deleteById(id);
    }
}
