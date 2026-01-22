package com.attemp3.sc.controllers.product;

import com.attemp3.sc.dtos.product.response.CardProductResponse;
import com.attemp3.sc.dtos.product.response.ListAllProductsResponse;
import com.attemp3.sc.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/api/public/products")
public class PublicProductController {

    private final ProductService productService;

    public PublicProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<CardProductResponse> showProductOnCard() {
        return productService.showProductCardStore();
    }
}
