package br.com.adrianorodrigues.posterr.e2e.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import br.com.adrianorodrigues.posterr.helper.context.AbstractContextMockDataBase;
import br.com.adrianorodrigues.posterr.helper.pool.application.rest.PostsDtoPool;
import br.com.adrianorodrigues.posterr.infra.repository.PostRepository;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsControllerTest extends AbstractContextMockDataBase {

	@LocalServerPort
	private int port;
	@Autowired
	private PostRepository postRepository;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@AfterEach
	void tearDown() {
		postRepository.deleteAll();
	}

	@Test
	void createPostWhenSuccessShouldReturnPost() throws JsonProcessingException {
		var post = PostsDtoPool.NEW_POST;

		RestAssured.given()
				.accept( ContentType.JSON )
				.contentType( ContentType.JSON )
				.body( post )
				.post( "/posts" )
				.then()
				.statusCode( HttpStatus.OK.value() )
				.body( "id", notNullValue() )
				.body( "content", equalTo( post.getContent() ) );
	}
}