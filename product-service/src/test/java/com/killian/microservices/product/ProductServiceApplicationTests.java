package com.killian.microservices.product;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

import io.restassured.RestAssured;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@Autowired
	private MongoDBContainer mongoDBContainer;

	@LocalServerPort
	private Integer port;

	@BeforeEach
	@SuppressWarnings("unused")
	void setup() {
		System.out.println("MongoDB Container URI in Test: " + mongoDBContainer.getReplicaSetUrl());
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	void shouldCreateProduct() {
		String requestBody = """
								{
				    "name": "iPhone 15",
				    "description": "Just fake description",
				    "price": 10000
				}
								""";
		RestAssured.given()
				.contentType("application/json").body(requestBody).when().post("/api/product").then()
				.statusCode(201).body("id", Matchers.notNullValue())
				.body("description", Matchers.equalTo("Just fake description")).body("price", Matchers.equalTo(10000));
	}

}
