package com.attemp3.sc.service;

import com.attemp3.sc.dtos.product.request.CreateProductRequest;
import com.attemp3.sc.dtos.product.request.UpdateProductRequest;
import com.attemp3.sc.dtos.product.response.ListAllProductsResponse;
import com.attemp3.sc.dtos.product.response.ReadOneProductResponse;
import com.attemp3.sc.entities.Product;
import com.attemp3.sc.mapper.ProductMapper;
import com.attemp3.sc.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ListAllProductsResponse> showAllProducts() {
        List<Product> productList = productRepository.findAll();

        return productList.stream()
                .map(ProductMapper::toAllProductsResponse)
                .toList();
    }

    public Product createProduct(CreateProductRequest request) {
        Product product = ProductMapper.toEntity(request);

        product.setReleaseDate(LocalDateTime.now());

        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(request.getCategory());
        product.setDiscount(request.getDiscount());
        product.setBrand(request.getBrand());
        product.setImageUrl(request.getImageUrl());
        product.setAvailable(request.isAvailable());

        return productRepository.save(product);
    }

    public ReadOneProductResponse readProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return ProductMapper.toReadOneProductResponse(product);
    }


}
