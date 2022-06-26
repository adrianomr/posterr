package br.com.adrianorodrigues.posterr.adapter.infra.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.adrianorodrigues.posterr.domain.PaginationFilter;
import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.domain.PostFilter;
import br.com.adrianorodrigues.posterr.infra.repository.PostRepository;
import br.com.adrianorodrigues.posterr.infra.repository.dto.PostsFilterDto;
import br.com.adrianorodrigues.posterr.util.builder.domain.PostBuilder;
import br.com.adrianorodrigues.posterr.util.context.AbstractContextMockDataBase;

@SpringBootTest
class PostRepositoryAdapterImplTest extends AbstractContextMockDataBase {

	@Autowired
	private PostRepository repository;
	@Autowired
	private PostRepositoryAdapter postRepositoryAdapter;

	@AfterEach
	void tearDown() {
		repository.deleteAll();
	}

	@Test
	void saveShouldSavePost() {
		var post = postRepositoryAdapter.save( PostBuilder.buildNewPost() );

		var postFromDb = repository.findById( post.getId() )
				.orElse( new Post() );
		assertThat( post )
				.hasFieldOrPropertyWithValue( "id", postFromDb.getId() )
				.hasFieldOrPropertyWithValue( "content", postFromDb.getContent() );
	}

	@Test
	void findAllPostsPaginatedShouldReturnPagesCount() {
		var filter = buildDefaultFilter(1);
		savePosts( 20 );

		var posts = postRepositoryAdapter.findAllPostsPaginated( filter );

		assertThat( posts ).hasFieldOrPropertyWithValue( "totalPages", 2 );
	}

	@ParameterizedTest
	@CsvSource({"20, 10, 1", "7, 7, 1", "17, 7, 2"})
	void findAllPostsPaginatedWhenMoreThanOnePageShouldReturn10PostsPerPage(int postsToCreate, int size, int page) {
		var filter = buildDefaultFilter(page);
		savePosts( postsToCreate );

		var posts = postRepositoryAdapter.findAllPostsPaginated( filter );

		assertThat( posts.getElements() )
				.hasSize( size );
	}

	@Test
	void findAllPostsPaginatedWhenFilteredByEndDateShouldReturnPosts() {
		Instant date = Instant.parse( "2000-01-23T01:23:45.678-03:00" );
		var filter = buildEndDateFilter( date );
		var oldPost = PostBuilder.buildNewPost();
		oldPost.setCreatedAt( date );
		savePosts( 17 );
		repository.save( oldPost );

		var posts = postRepositoryAdapter.findAllPostsPaginated( filter );

		assertThat( posts.getElements() )
				.hasSize( 1 );
	}

	@Test
	void findAllPostsPaginatedWhenFilteredByBeginDateShouldReturnPosts() {
		Instant date = Instant.now().plus( 2, ChronoUnit.DAYS );
		var filter = buildBeginDateFilter( date );
		var newPost = PostBuilder.buildNewPost();
		newPost.setCreatedAt( date );
		savePosts( 17 );
		repository.save( newPost );

		var posts = postRepositoryAdapter.findAllPostsPaginated( filter );

		assertThat( posts.getElements() )
				.hasSize( 1 );
	}

	@Test
	void findAllPostsPaginatedWhenFilteredByBeginDateAndEndDateShouldReturnPosts() {
		Instant date = Instant.now().plus( 2, ChronoUnit.DAYS );
		var filter = buildBeginDateAndEndDateFilter( date );
		savePosts( 17 );
		var newPost = PostBuilder.buildNewPost();
		newPost.setCreatedAt( date );
		repository.save( newPost );
		newPost = PostBuilder.buildNewPost();
		newPost.setCreatedAt( date );
		repository.save( newPost );

		var posts = postRepositoryAdapter.findAllPostsPaginated( filter );

		assertThat( posts.getElements() )
				.hasSize( 2 );
	}

	@Test
	void findAllPostsPaginatedWhenFilteredByUserShouldReturnPosts() {
		Instant date = Instant.now().plus( 2, ChronoUnit.DAYS );
		UUID userId = UUID.fromString( "a4ce0058-cd5d-456b-8f30-7fd85e3650d5" );
		var filter = buildUserFilter( userId );
		var newPost = PostBuilder.buildNewPost();
		newPost.setUserId( userId );
		savePosts( 17 );
		repository.save( newPost );

		var posts = postRepositoryAdapter.findAllPostsPaginated( filter );

		assertThat( posts.getElements() )
				.hasSize( 1 );
	}

	@Test
	void findAllPostsPaginatedShouldReturnPostsInOrderFromNewerToOlder() {
		Instant date = Instant.now().minus( 2, ChronoUnit.DAYS );
		var filter = buildDefaultFilter( 1 );
		var newPost = PostBuilder.buildNewPost();
		newPost.setCreatedAt( Instant.now() );
		var oldPost = PostBuilder.buildNewPost();
		oldPost.setCreatedAt( date );
		repository.save( oldPost );
		repository.save( newPost );

		var posts = postRepositoryAdapter.findAllPostsPaginated( filter );

		assertThat( posts.getElements().get( 0 ).getId() )
				.isEqualTo( newPost.getId() );
	}

	private PaginationFilter<PostFilter> buildUserFilter(UUID id) {
		return buildFilter( 1, PostFilter.builder().userId( id ).build() );
	}

	private PaginationFilter<PostFilter> buildBeginDateAndEndDateFilter(Instant date) {
		return buildFilter( 1, PostFilter.builder().beginDate( date.minus( 1, ChronoUnit.DAYS ) ).endDate( date.plus( 5, ChronoUnit.DAYS ) ).build() );
	}

	private PaginationFilter<PostFilter> buildBeginDateFilter(Instant date) {
		return buildFilter( 1, PostFilter.builder().beginDate( date.minus( 1, ChronoUnit.DAYS ) ).build() );
	}

	private PaginationFilter<PostFilter> buildEndDateFilter(Instant date) {
		return buildFilter( 1, PostFilter.builder().endDate( date.plus( 1, ChronoUnit.DAYS ) ).build() );
	}

	private PaginationFilter<PostFilter> buildDefaultFilter(int page) {
		return buildFilter( page, new PostFilter() );
	}

	private PaginationFilter<PostFilter> buildFilter(int page, PostFilter filter) {
		return new PaginationFilter<>( page, 10, filter );
	}

	private void savePosts(int total) {
		for (int i = 0; i < total; i++) {
			repository.save( PostBuilder.buildNewPost() );
		}
	}
}