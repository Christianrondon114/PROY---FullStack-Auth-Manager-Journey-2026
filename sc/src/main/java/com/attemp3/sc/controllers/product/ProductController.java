package com.attemp3.sc.controllers.product;

import com.attemp3.sc.dtos.product.request.CreateProductRequest;
import com.attemp3.sc.dtos.product.request.UpdateProductRequest;
import com.attemp3.sc.dtos.product.response.ListAllProductsResponse;
import com.attemp3.sc.dtos.product.response.ReadOneProductResponse;
import com.attemp3.sc.entities.Product;
import com.attemp3.sc.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ListAllProductsResponse> showAllProducts() {
        return productService.showAllProducts();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody @Valid CreateProductRequest request) {
        return productService.createProduct(request);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Product updateProduct(@PathVariable Long id, @RequestBody @Valid UpdateProductRequest request) {
        return productService.updateProduct(id, request);
    }

    @GetMapping(path ="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ReadOneProductResponse readProductById(@PathVariable Long id) {
        return productService.readProductById(id);
    }

}
