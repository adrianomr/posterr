package br.com.adrianorodrigues.posterr.application.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import br.com.adrianorodrigues.posterr.adapter.application.rest.PostsControllerAdapter;
import br.com.adrianorodrigues.posterr.application.rest.dto.PostDto;
import br.com.adrianorodrigues.posterr.helper.context.AbstractContextMockDataBase;
import br.com.adrianorodrigues.posterr.helper.pool.application.rest.PostsDtoPool;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsControllerTest extends AbstractContextMockDataBase {

	@LocalServerPort
	int port;
	@MockBean
	PostsControllerAdapter adapter;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@Test
	void createPostWhenSuccessShouldReturnPost() {
		PostDto createdPost = PostsDtoPool.CREATED_POST;
		when( adapter.createPost( any() ) )
				.thenReturn( createdPost );

		RestAssured.given()
				.body( "{}" )
				.accept( ContentType.JSON )
				.contentType( ContentType.JSON )
				.post( "/posts" )
				.then()
				.statusCode( HttpStatus.OK.value() )
				.body( "id", equalTo( createdPost.getId().toString() ) )
				.body( "content", equalTo( createdPost.getContent() ) );
	}
}