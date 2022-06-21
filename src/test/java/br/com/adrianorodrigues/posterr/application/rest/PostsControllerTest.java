package br.com.adrianorodrigues.posterr.application.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsControllerTest {

	@LocalServerPort
	int port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@Test
	void createPost() {
		RestAssured.given()
				.body( "{}" )
				.accept( ContentType.JSON )
				.contentType( ContentType.JSON )
				.post( "/posts" )
				.then()
				.statusCode( HttpStatus.OK.value() )
				.log()
				.all();
	}
}