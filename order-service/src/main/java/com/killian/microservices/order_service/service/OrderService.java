package com.killian.microservices.order_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.killian.microservices.order_service.dto.OrderRequest;
import com.killian.microservices.order_service.model.Order;
import com.killian.microservices.order_service.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void placeOrder(OrderRequest request) {
        Order order = Order.fromRequest(request);
        orderRepository.save(order);
    }
}
