package com.killian.microservices.product.dto;

import java.math.BigDecimal;

import com.killian.microservices.product.model.Product;

public record ProductResponse(String id, String name, String description, BigDecimal price) {

    public static ProductResponse fromProduct(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }
}
