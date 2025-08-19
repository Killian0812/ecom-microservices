package com.killian.microservices.order_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI orderServiceApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Order Service API")
                        .version("0.0.1")
                        .description("API for managing orders in the microservices architecture")
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0")));
    }
}
