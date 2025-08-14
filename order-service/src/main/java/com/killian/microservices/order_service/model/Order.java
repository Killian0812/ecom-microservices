package com.killian.microservices.order_service.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.killian.microservices.order_service.dto.OrderRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;

    public static Order fromRequest(OrderRequest request) {
        return Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .skuCode(request.skuCode())
                .price(request.price())
                .quantity(request.quantity())
                .build();
    }
}
