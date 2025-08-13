package com.killian.microservices.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.killian.microservices.product.dto.ProductRequest;
import com.killian.microservices.product.dto.ProductResponse;
import com.killian.microservices.product.model.Product;
import com.killian.microservices.product.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest request) {
        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price()).build();
        productRepository.save(product);
        return ProductResponse.fromProduct(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map((product) -> ProductResponse.fromProduct(product)).toList();
    }
}
