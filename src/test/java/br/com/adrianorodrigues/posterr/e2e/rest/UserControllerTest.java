package br.com.adrianorodrigues.posterr.e2e.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.time.Instant;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import br.com.adrianorodrigues.posterr.application.rest.dto.PostDto;
import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.infra.repository.PostRepository;
import br.com.adrianorodrigues.posterr.infra.repository.UserRepository;
import br.com.adrianorodrigues.posterr.mapper.application.rest.PostDtoMapper;
import br.com.adrianorodrigues.posterr.util.builder.application.rest.PostsDtoBuilder;
import br.com.adrianorodrigues.posterr.util.builder.domain.PostBuilder;
import br.com.adrianorodrigues.posterr.util.builder.domain.UserBuilder;
import br.com.adrianorodrigues.posterr.util.context.AbstractContextMockDataBase;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest extends AbstractContextMockDataBase {

	@LocalServerPort
	private int port;
	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@Test
	void getUserShouldReturnData() {
		var user = UserBuilder.buildUser1();
		userRepository.save( UserBuilder.buildUser1() );

		executeGetRequest( user.getId().toString() )
				.then()
				.statusCode( HttpStatus.OK.value() )
				.body( "id", equalTo( user.getId().toString() ))
				.body( "dailyPostsAmount", equalTo( user.getDailyPostsAmount() ) )
				.body( "lastPostDate", equalTo( user.getLastPostDate() ) )
				.body( "postsAmount", equalTo( user.getPostsAmount().intValue() ) )
				.body( "dailyPostsAmount", equalTo( user.getDailyPostsAmount() ) )
				.body( "createdAt", notNullValue() )
				.body( "username", equalTo( user.getUsername() ) );
	}

	@Test
	void getUserWithPostsInSameDayShouldReturnData() {
		var user = UserBuilder.buildUserWithPostInSameDay();
		userRepository.save( UserBuilder.buildUserWithPostInSameDay() );

		executeGetRequest( user.getId().toString() )
				.then()
				.statusCode( HttpStatus.OK.value() )
				.body( "id", equalTo( user.getId().toString() ))
				.body( "dailyPostsAmount", equalTo( user.getDailyPostsAmount() ) )
				.body( "lastPostDate", equalTo( user.getLastPostDate().toString() ) )
				.body( "postsAmount", equalTo( user.getPostsAmount().intValue() ) )
				.body( "dailyPostsAmount", equalTo( user.getDailyPostsAmount() ) )
				.body( "createdAt", notNullValue() )
				.body( "username", equalTo( user.getUsername() ) );
	}

	private Response executeGetRequest(String userId) {
		return RestAssured.given()
				.header( "x-user-id", userId )
				.accept( ContentType.JSON )
				.contentType( ContentType.JSON )
				.get( "/users/current" );
	}

}