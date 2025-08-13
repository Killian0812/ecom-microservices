package com.killian.microservices.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.killian.microservices.product.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
