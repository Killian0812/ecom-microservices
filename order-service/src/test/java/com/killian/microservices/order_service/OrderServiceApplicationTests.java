package com.killian.microservices.order_service;

import java.util.Map;

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
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;

import com.killian.microservices.order_service.repository.OrderRepository;
import com.killian.microservices.order_service.stub.InventoryServiceStub;

import io.restassured.RestAssured;

@TestMethodOrder(OrderAnnotation.class)
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

	@Autowired
	private OrderRepository orderRepository;

	@LocalServerPort
	private Integer port;

	private static final String SKU_CODE = "IPHONE-15";
	private static final int PRICE = 10000;
	private static final int QUANTITY = 1;
	private static final String ORDER_ENDPOINT = "/api/order";

	@BeforeEach
	@SuppressWarnings("unused")
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;

		InventoryServiceStub.stubInventoryCall(SKU_CODE, QUANTITY);
	}

	@Test
	@Order(1)
	void shouldCreateOrder() {
		Map<String, Object> requestBody = Map.of(
				"skuCode", SKU_CODE,
				"price", PRICE,
				"quantity", QUANTITY);
		RestAssured.given()
				.contentType("application/json").body(requestBody).when().post(ORDER_ENDPOINT).then()
				.statusCode(200);
	}

	@Test
	@Order(2)
	void shouldQueryPlacedOrderFromDatabase() {
		var orderOpt = orderRepository.findFirstBySkuCode(SKU_CODE);
		assertThat(orderOpt).isPresent();
		assertThat(orderOpt.get().getQuantity()).isEqualTo(QUANTITY);
		assertThat(orderOpt.get().getPrice())
				.isEqualByComparingTo(Integer.toString(PRICE));
	}
}
