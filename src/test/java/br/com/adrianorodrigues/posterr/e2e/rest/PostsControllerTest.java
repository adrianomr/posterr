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

	@Test
	void findPostsPaginated() {
		for (int i = 0; i < 5; i++) {
			var post = PostsDtoBuilder.buildNewPost();
			executePostRequest( post );
		}
		RestAssured.given()
				.header( "x-user-id", UserBuilder.buildUser1().getId().toString() )
				.accept( ContentType.JSON )
				.contentType( ContentType.JSON )
				.get( "/posts" )
				.then()
				.statusCode( HttpStatus.OK.value() )
				.body( "totalPages", equalTo( 1 ) )
				.body( "totalElements", equalTo( 6 ) )
				.log()
				.all();
	}

	@Test
	void findPostsPaginatedWhenPageSizeIs5ShouldReturnOnlyFiveElements() {
		for (int i = 0; i < 5; i++) {
			var post = PostsDtoBuilder.buildNewPost();
			executePostRequest( post );
		}
		RestAssured.given()
				.header( "x-user-id", UserBuilder.buildUser1().getId().toString() )
				.param( "pageSize", 5 )
				.accept( ContentType.JSON )
				.contentType( ContentType.JSON )
				.get( "/posts" )
				.then()
				.statusCode( HttpStatus.OK.value() )
				.body( "totalPages", equalTo( 2 ) )
				.body( "totalElements", equalTo( 5 ) )
				.log()
				.all();
	}

	@Test
	void findPostsPaginatedWhenSecondPageRequestShouldReturnSecondPage() {
		for (int i = 0; i < 15; i++) {
			var post = PostBuilder.buildNewPost();
			postRepository.save( post );
		}
		RestAssured.given()
				.header( "x-user-id", UserBuilder.buildUser1().getId().toString() )
				.param( "page", "2" )
				.accept( ContentType.JSON )
				.contentType( ContentType.JSON )
				.get( "/posts" )
				.then()
				.statusCode( HttpStatus.OK.value() )
				.body( "totalPages", equalTo( 2 ) )
				.body( "totalElements", equalTo( 6 ) )
				.body( "page", equalTo( 2 ) )
				.log()
				.all();
	}

	@Test
	void findPostsPaginatedWhenFilteredByBeginDateShouldReturnPosts() {
		for (int i = 0; i < 2; i++) {
			var post = PostsDtoBuilder.buildNewPost();
			executePostRequest( post );
		}
		var date = Instant.now();
		for (int i = 0; i < 3; i++) {
			var post = PostsDtoBuilder.buildNewPost();
			executePostRequest( post );
		}
		RestAssured.given()
				.header( "x-user-id", UserBuilder.buildUser1().getId().toString() )
				.param( "beginDate", date.toString() )
				.accept( ContentType.JSON )
				.contentType( ContentType.JSON )
				.get( "/posts" )
				.then()
				.statusCode( HttpStatus.OK.value() )
				.body( "totalPages", equalTo( 1 ) )
				.body( "totalElements", equalTo( 3 ) )
				.body( "page", equalTo( 1 ) )
				.log()
				.all();
	}

	@Test
	void findPostsPaginatedWhenFilteredByEndDateShouldReturnPosts() {
		for (int i = 0; i < 3; i++) {
			var post = PostsDtoBuilder.buildNewPost();
			executePostRequest( post );
		}
		var date = Instant.now();
		for (int i = 0; i < 3; i++) {
			var post = PostsDtoBuilder.buildNewPost();
			executePostRequest( post );
		}
		RestAssured.given()
				.header( "x-user-id", UserBuilder.buildUser1().getId().toString() )
				.param( "endDate", date.toString() )
				.accept( ContentType.JSON )
				.contentType( ContentType.JSON )
				.get( "/posts" )
				.then()
				.statusCode( HttpStatus.OK.value() )
				.body( "totalPages", equalTo( 1 ) )
				.body( "totalElements", equalTo( 4 ) )
				.body( "page", equalTo( 1 ) )
				.log()
				.all();
	}

	@Test
	void findPostsPaginatedWhenFilteredByMyPostsShouldReturnOnlyUserPosts() {
		for (int i = 0; i < 3; i++) {
			var post = PostsDtoBuilder.buildNewPost();
			executePostRequest( post );
		}
		var date = Instant.now();
		for (int i = 0; i < 3; i++) {
			var post = PostsDtoBuilder.buildNewPost();
			executePostRequest( post , "a4ce0058-cd5d-456b-8f30-7fd85e3650d5");
		}
		RestAssured.given()
				.header( "x-user-id", "a4ce0058-cd5d-456b-8f30-7fd85e3650d5" )
				.param( "myPosts", true )
				.accept( ContentType.JSON )
				.contentType( ContentType.JSON )
				.get( "/posts" )
				.then()
				.statusCode( HttpStatus.OK.value() )
				.body( "totalPages", equalTo( 1 ) )
				.body( "totalElements", equalTo( 3 ) )
				.body( "page", equalTo( 1 ) )
				.log()
				.all();
	}

	private Response executePostRequest(PostDto post) {
		return executePostRequest( post, UserBuilder.buildUser1().getId().toString() );
	}

	private Response executePostRequest(PostDto post, String userId) {
		return RestAssured.given()
				.header( "x-user-id", userId )
				.accept( ContentType.JSON )
				.contentType( ContentType.JSON )
				.body( post )
				.post( "/posts" );
	}

}