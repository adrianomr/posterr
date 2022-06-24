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
import br.com.adrianorodrigues.posterr.util.context.AbstractContextMockDataBase;
import br.com.adrianorodrigues.posterr.util.pool.application.rest.PostsDtoPool;
import br.com.adrianorodrigues.posterr.util.pool.domain.PostsPool;
import br.com.adrianorodrigues.posterr.infra.repository.PostRepository;
import br.com.adrianorodrigues.posterr.mapper.application.rest.PostDtoMapper;
import br.com.adrianorodrigues.posterr.util.pool.domain.UsersPool;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsControllerTest extends AbstractContextMockDataBase {

	@LocalServerPort
	private int port;
	@Autowired
	private PostRepository postRepository;
	private Post savedPost;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
		Post post = PostsPool.NEW_POST;
		savedPost = postRepository.save( post );
	}

	@AfterEach
	void tearDown() {
		postRepository.deleteAll();
	}

	@Test
	void createPostWhenSuccessShouldReturnPost() {
		var post = PostsDtoPool.NEW_POST;

		executePostRequest( post )
				.then()
				.statusCode( HttpStatus.OK.value() )
				.body( "id", notNullValue() )
				.body( "content", equalTo( post.getContent() ) )
				.body( "type", equalTo( post.getType().toString() ) );
	}

	@Test
	void createPostWhenExecutedMoreThanFiveTimesInSameDayShouldReturnForbidden() {
		var post = PostsDtoPool.NEW_POST;

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
		var post = PostDtoMapper.INSTANCE.map( PostsDtoPool.NEW_QUOTE_POST_WITHOUT_ORIGINAL_POST );
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
		var post = PostDtoMapper.INSTANCE.map( PostsDtoPool.NEW_REPOST_POST_WITHOUT_ORIGINAL_POST );
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
		var post = PostDtoMapper.INSTANCE.map( PostsDtoPool.NEW_POST );
		post.setOriginalPostId( savedPost.getId() );

		executePostRequest( post )
				.then()
				.statusCode( HttpStatus.BAD_REQUEST.value() );
	}

	@Test
	void createPostWhenQuoteWithoutOriginalPostShouldReturnBadRequest() {
		var post = PostDtoMapper.INSTANCE.map( PostsDtoPool.NEW_QUOTE_POST_WITHOUT_ORIGINAL_POST );

		executePostRequest( post )
				.then()
				.statusCode( HttpStatus.BAD_REQUEST.value() );
	}

	@Test
	void createPostWhenRepostWithoutOriginalPostShouldReturnBadRequest() {
		var post = PostDtoMapper.INSTANCE.map( PostsDtoPool.NEW_REPOST_POST_WITHOUT_ORIGINAL_POST );

		executePostRequest( post )
				.then()
				.statusCode( HttpStatus.BAD_REQUEST.value() );
	}

	@Test
	void createPostWhenNoUserIdHeaderShouldReturnUnauthorized() {
		var post = PostsDtoPool.NEW_POST;

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
				.header( "x-user-id", UsersPool.USER_1.getId().toString() )
				.accept( ContentType.JSON )
				.contentType( ContentType.JSON )
				.body( post )
				.post( "/posts" );
	}


}