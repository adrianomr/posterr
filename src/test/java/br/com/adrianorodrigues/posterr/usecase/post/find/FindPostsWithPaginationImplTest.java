package br.com.adrianorodrigues.posterr.usecase.post.find;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.adrianorodrigues.posterr.adapter.infra.repository.PostRepositoryAdapter;
import br.com.adrianorodrigues.posterr.domain.Page;
import br.com.adrianorodrigues.posterr.domain.PaginationFilter;
import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.domain.PostFilter;

@ExtendWith(MockitoExtension.class)
class FindPostsWithPaginationImplTest {

	public static final UUID USER_ID = UUID.fromString( "d201e18b-83b0-4ea1-928d-81dee88eb8eb" );
	@Mock
	PostRepositoryAdapter adapter;
	@InjectMocks
	private FindPostsWithPaginationImpl findPostsWithPagination;

	@Test
	void executeShouldCallAdapter() {
		var filter = new PaginationFilter<>( 1, 10, new PostFilter() );
		findPostsWithPagination.execute( filter );
		verify( adapter )
				.findAllPostsPaginated( filter );
	}

	@Test
	void executeWhenMyPostsIsNullShouldReturnAllPosts() {
		var filter = new PaginationFilter<>( 1, 10, PostFilter.builder().userId( USER_ID ).build() );

		findPostsWithPagination.execute( filter );

		assertThat( filter.getFilter().getUserId() )
				.isNull();
	}

	@Test
	void executeWhenMyPostsIsFalseShouldReturnAllPosts() {
		var filter = new PaginationFilter<>( 1, 10, PostFilter.builder().userId( USER_ID ).myPosts( false ).build() );

		findPostsWithPagination.execute( filter );

		assertThat( filter.getFilter().getUserId() )
				.isNull();
	}

	@Test
	void executeWhenMyPostsIsTrueShouldReturnMyPosts() {
		var filter = new PaginationFilter<>( 1, 10, PostFilter.builder().userId( USER_ID ).myPosts( true ).build() );

		findPostsWithPagination.execute( filter );

		assertThat( filter.getFilter().getUserId() )
				.isEqualTo(USER_ID);
	}

	@Test
	void executeShouldReturnPage() {
		var filter = new PaginationFilter<>( 1, 10, new PostFilter() );
		var dbPage = new Page<Post>();
		when( adapter.findAllPostsPaginated( filter ) )
				.thenReturn( dbPage );
		var page = findPostsWithPagination.execute( filter );

		assertEquals( dbPage, page );
	}
}