package com.killian.microservices.order_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.killian.microservices.order_service.dto.OrderRequest;
import com.killian.microservices.order_service.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping()
    public String placeOrder(@RequestBody OrderRequest request) {
        orderService.placeOrder(request);
        return "Order place successfully";
    }
}
