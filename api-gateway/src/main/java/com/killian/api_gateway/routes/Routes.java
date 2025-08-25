package com.killian.api_gateway.routes;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@SuppressWarnings("deprecation")
@Configuration(proxyBeanMethods = false)
public class Routes {
        @Value("${product.service.url}")
        private String productServiceUrl;
        @Value("${order.service.url}")
        private String orderServiceUrl;
        @Value("${inventory.service.url}")
        private String inventoryServiceUrl;

        @Bean
        public RouterFunction<ServerResponse> productServiceRoute() {
                return route("product_service")
                                .route(RequestPredicates.path("/api/product"), http(productServiceUrl))
                                .filter(CircuitBreakerFilterFunctions.circuitBreaker("productServiceCircuitBreaker",
                                                URI.create("forward:/fallbackRoute")))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> orderServiceRoute() {
                return route("order_service")
                                .route(RequestPredicates.path("/api/order"), http(orderServiceUrl))
                                .filter(CircuitBreakerFilterFunctions.circuitBreaker("orderServiceCircuitBreaker",
                                                URI.create("forward:/fallbackRoute")))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> inventoryServiceRoute() {
                return route("inventory_service")
                                .route(RequestPredicates.path("/api/inventory"), http(inventoryServiceUrl))
                                .filter(CircuitBreakerFilterFunctions.circuitBreaker("inventoryServiceCircuitBreaker",
                                                URI.create("forward:/fallbackRoute")))
                                .build();
        }

        // No OpenAPI config needed, using aggregated documentation only
        @Bean
        public RouterFunction<ServerResponse> productServiceSwaggerRoute() {
                return route("product_service_swagger")
                                .route(RequestPredicates.path("/aggregate/product-service/api-docs"),
                                                HandlerFunctions.http(productServiceUrl))
                                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                                                "productServiceSwaggerCircuitBreaker",
                                                URI.create("forward:/fallbackRoute")))
                                .filter(setPath("/v3/api-docs"))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> orderServiceSwaggerRoute() {
                return route("order_service_swagger")
                                .route(RequestPredicates.path("/aggregate/order-service/api-docs"),
                                                HandlerFunctions.http(orderServiceUrl))
                                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                                                "orderServiceSwaggerCircuitBreaker",
                                                URI.create("forward:/fallbackRoute")))
                                .filter(setPath("/v3/api-docs"))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> inventoryServiceSwaggerRoute() {
                return route("inventory_service_swagger")
                                .route(RequestPredicates.path("/aggregate/inventory-service/api-docs"),
                                                HandlerFunctions.http(inventoryServiceUrl))
                                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                                                "inventoryServiceSwaggerCircuitBreaker",
                                                URI.create("forward:/fallbackRoute")))
                                .filter(setPath("/v3/api-docs"))
                                .build();
        }

        @Bean
        public RouterFunction<ServerResponse> fallbackRoute() {
                return route("fallbackRoute")
                                .GET("/fallbackRoute", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                                                .body("Service is currently unavailable. Please try again later."))
                                .build();
        }
}