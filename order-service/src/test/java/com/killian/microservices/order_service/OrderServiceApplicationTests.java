package com.killian.microservices.order_service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import com.killian.microservices.order_service.repository.OrderRepository;

import io.restassured.RestAssured;

@TestMethodOrder(OrderAnnotation.class)
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

	@Autowired
	private OrderRepository orderRepository;

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	@Order(1)
	void shouldCreateOrder() {
		String requestBody = """
						{
				"skuCode": "iphone_15",
				"price": 10000,
				"quantity": 1
				}
						""";

		RestAssured.given()
				.contentType("application/json").body(requestBody).when().post("/api/order").then()
				.statusCode(200);
	}

	@Test
	@Order(2)
	void shouldQueryPlacedOrderFromDatabase() {
		var orderOpt = orderRepository.findFirstBySkuCode("iphone_15");
		assertThat(orderOpt).isPresent();
		assertThat(orderOpt.get().getQuantity()).isEqualTo(1);
		assertThat(orderOpt.get().getPrice())
				.isEqualByComparingTo("10000");
	}
}
