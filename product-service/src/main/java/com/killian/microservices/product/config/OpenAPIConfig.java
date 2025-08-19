package com.killian.microservices.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI productServiceApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Product Service API")
                        .version("0.0.1")
                        .description("API for managing products in the microservices architecture")
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0")));
    }
}
