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

import br.com.adrianorodrigues.posterr.application.rest.dto.PostDto;
import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.infra.repository.UserRepository;
import br.com.adrianorodrigues.posterr.util.context.AbstractContextMockDataBase;
import br.com.adrianorodrigues.posterr.util.builder.application.rest.PostsDtoBuilder;
import br.com.adrianorodrigues.posterr.util.builder.domain.PostBuilder;
import br.com.adrianorodrigues.posterr.infra.repository.PostRepository;
import br.com.adrianorodrigues.posterr.mapper.application.rest.PostDtoMapper;
import br.com.adrianorodrigues.posterr.util.builder.domain.UserBuilder;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsControllerTest extends AbstractContextMockDataBase {

	@LocalServerPort
	private int port;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;
	private Post savedPost;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
		var post = PostBuilder.buildNewPost();
		savedPost = postRepository.save( post );
		userRepository.save( UserBuilder.buildUser1() );
	}

	@AfterEach
	void tearDown() {
		postRepository.deleteAll();
	}

	@Test
	void createPostWhenSuccessShouldReturnPost() {
		var post = PostsDtoBuilder.buildNewPost();

		executePostRequest( post )
				.then()
				.statusCode( HttpStatus.OK.value() )
				.body( "id", notNullValue() )
				.body( "content", equalTo( post.getContent() ) )
				.body( "type", equalTo( post.getType().toString() ) );
	}

	@Test
	void createPostWhenExecutedMoreThanFiveTimesInSameDayShouldReturnForbidden() {
		var post = PostsDtoBuilder.buildNewPost();

		for (int i = 0; i < 5; i++) {
			executePostRequest( post )
					.then()
					.statusCode( HttpStatus.OK.value() )
					.body( "id", notNullValue() )
					.body( "content", equalTo( post.getContent() ) )
					.body( "type", equalTo( post.getType().toString() ) );
		}

		executePostRequest( post )
				.then()
				.statusCode( HttpStatus.FORBIDDEN.value() );
	}

	@Test
	void createPostWhenQuoteWithOriginalPostShouldReturnSuccess() {
		var post = PostDtoMapper.INSTANCE.map( PostsDtoBuilder.buildNewQuotePostWithoutOriginalPost() );
		post.setOriginalPostId( savedPost.getId() );

		executePostRequest( post )
				.then()
				.statusCode( HttpStatus.OK.value() )
				.body( "id", notNullValue() )
				.body( "content", equalTo( post.getContent() ) )
				.body( "type", equalTo( post.getType().toString() ) )
				.body( "originalPostId", equalTo( savedPost.getId().toString() ) );
	}

	@Test
	void createPostWhenRepostWithOriginalPostShouldReturnSuccess() {
		var post = PostDtoMapper.INSTANCE.map( PostsDtoBuilder.buildNewRepostPostWithoutOriginalPost() );
		post.setOriginalPostId( savedPost.getId() );

		executePostRequest( post )
				.then()
				.statusCode( HttpStatus.OK.value() )
				.body( "id", notNullValue() )
				.body( "content", equalTo( post.getContent() ) )
				.body( "type", equalTo( post.getType().toString() ) )
				.body( "originalPostId", equalTo( savedPost.getId().toString() ) );
	}

	@Test
	void createPostWhenRegularWithOriginalPostShouldReturnBadRequest() {
		var post = PostDtoMapper.INSTANCE.map( PostsDtoBuilder.buildNewPost() );
		post.setOriginalPostId( savedPost.getId() );

		executePostRequest( post )
				.then()
				.statusCode( HttpStatus.BAD_REQUEST.value() );
	}

	@Test
	void createPostWhenQuoteWithoutOriginalPostShouldReturnBadRequest() {
		var post = PostDtoMapper.INSTANCE.map( PostsDtoBuilder.buildNewQuotePostWithoutOriginalPost() );

		executePostRequest( post )
				.then()
				.statusCode( HttpStatus.BAD_REQUEST.value() );
	}

	@Test
	void createPostWhenRepostWithoutOriginalPostShouldReturnBadRequest() {
		var post = PostDtoMapper.INSTANCE.map( PostsDtoBuilder.buildNewRepostPostWithoutOriginalPost() );

		executePostRequest( post )
				.then()
				.statusCode( HttpStatus.BAD_REQUEST.value() );
	}

	@Test
	void createPostWhenNoUserIdHeaderShouldReturnUnauthorized() {
		var post = PostsDtoBuilder.buildNewPost();

		RestAssured.given()
				.accept( ContentType.JSON )
				.contentType( ContentType.JSON )
				.body( post )
				.post( "/posts" )
				.then()
				.statusCode( HttpStatus.UNAUTHORIZED.value() );
	}

	private Response executePostRequest(PostDto post) {
		return RestAssured.given()
				.header( "x-user-id", UserBuilder.buildUser1().getId().toString() )
				.accept( ContentType.JSON )
				.contentType( ContentType.JSON )
				.body( post )
				.post( "/posts" );
	}


}