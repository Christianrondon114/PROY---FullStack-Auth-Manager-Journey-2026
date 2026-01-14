package com.attemp3.sc.mapper;

import com.attemp3.sc.dtos.product.request.CreateProductRequest;
import com.attemp3.sc.dtos.product.response.ListAllProductsResponse;
import com.attemp3.sc.dtos.product.response.ReadOneProductResponse;
import com.attemp3.sc.dtos.user.response.ReadOneUserResponse;
import com.attemp3.sc.entities.Product;
import com.attemp3.sc.entities.User;

public class ProductMapper {

    // ---toAllProductsResponse ---
    public static ListAllProductsResponse toAllProductsResponse(Product product) {
        return new ListAllProductsResponse(product.getProductId(), product.getName(), product.getPrice(),product.getStock(),product.isAvailable());
    }

    // --- CreateProductRequest ---

    public static Product toEntity(CreateProductRequest request){
        return new Product(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStock(),
                request.getCategory(),
                request.getDiscount(),
                request.getBrand(),
                request.getImageUrl(),
                request.isAvailable());
    }

    // --- toReadOneProductResponse ---
    public static ReadOneProductResponse toReadOneProductResponse (Product product){
        return new ReadOneProductResponse(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory(),
                product.getDiscount(),
                product.getBrand(),
                product.getImageUrl(),
                product.isAvailable(),
                product.getReleaseDate());
    }
}
