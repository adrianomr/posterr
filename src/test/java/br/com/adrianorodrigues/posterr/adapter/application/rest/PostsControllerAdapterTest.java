package br.com.adrianorodrigues.posterr.adapter.application.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.adrianorodrigues.posterr.application.rest.dto.PostDto;
import br.com.adrianorodrigues.posterr.application.rest.dto.PostFilterDto;
import br.com.adrianorodrigues.posterr.domain.Page;
import br.com.adrianorodrigues.posterr.domain.PaginationFilter;
import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.domain.PostFilter;
import br.com.adrianorodrigues.posterr.usecase.post.find.FindPostsWithPagination;
import br.com.adrianorodrigues.posterr.util.builder.application.rest.PostFilterDtoBuilder;
import br.com.adrianorodrigues.posterr.util.builder.application.rest.PostsDtoBuilder;
import br.com.adrianorodrigues.posterr.util.builder.domain.PostBuilder;
import br.com.adrianorodrigues.posterr.usecase.post.create.CreatePost;
import br.com.adrianorodrigues.posterr.util.builder.domain.UserBuilder;

@ExtendWith(MockitoExtension.class)
class PostsControllerAdapterTest {

	public static final UUID USER_ID = UUID.fromString( "d201e18b-83b0-4ea1-928d-81dee88eb8eb" );
	@Mock
	CreatePost createPost;
	@Mock
	FindPostsWithPagination findPostsWithPagination;
	@InjectMocks
	PostsControllerAdapter adapter;
	@Captor
	ArgumentCaptor<Post> postCaptor;
	@Captor
	ArgumentCaptor<PaginationFilter<PostFilter>> paginationFilterCaptor;

	@Test
	void createPostShouldCallUseCase() {
		Post createdPost = PostBuilder.buildCreatedPost();
		when( createPost.execute( any() ) )
				.thenReturn( createdPost );

		adapter.createPost( PostsDtoBuilder.buildNewQuotePostWithoutOriginalPost(),
				UserBuilder.buildUser1().getId().toString() );

		verify( createPost )
				.execute( postCaptor.capture() );
		assertThat( postCaptor.getValue() )
				.hasFieldOrPropertyWithValue( "id", null )
				.hasFieldOrPropertyWithValue( "content",
						PostsDtoBuilder.buildNewQuotePostWithoutOriginalPost().getContent() )
				.hasFieldOrPropertyWithValue( "type", PostsDtoBuilder.buildNewQuotePostWithoutOriginalPost().getType() )
				.hasFieldOrPropertyWithValue( "originalPost", null );
	}

	@Test
	void createPostWhenPostHasOriginalPostShouldCallWithOriginalPost() {
		Post createdPost = PostBuilder.buildCreatedPost();
		var postDto = PostsDtoBuilder.buildNewPostWithOriginalPost();
		when( createPost.execute( any() ) )
				.thenReturn( createdPost );

		adapter.createPost( postDto, UserBuilder.buildUser1().getId().toString() );

		verify( createPost )
				.execute( postCaptor.capture() );
		assertThat( postCaptor.getValue() )
				.hasFieldOrPropertyWithValue( "id", null )
				.hasFieldOrPropertyWithValue( "content", postDto.getContent() )
				.hasFieldOrPropertyWithValue( "type", postDto.getType() )
				.hasFieldOrPropertyWithValue( "originalPost.id", postDto.getOriginalPostId() )
				.hasFieldOrPropertyWithValue( "userId", UserBuilder.buildUser1().getId() );
	}

	@Test
	void createPostShouldReturnPostData() {
		Post createdPost = PostBuilder.buildNewPostWithOriginalPost();
		when( createPost.execute( any() ) )
				.thenReturn( createdPost );

		var post = adapter.createPost( PostsDtoBuilder.buildNewQuotePostWithoutOriginalPost(),
				UserBuilder.buildUser1().getId().toString() );

		assertThat( post )
				.hasFieldOrPropertyWithValue( "content", createdPost.getContent() )
				.hasFieldOrPropertyWithValue( "id", createdPost.getId() )
				.hasFieldOrPropertyWithValue( "type", createdPost.getType() )
				.hasFieldOrPropertyWithValue( "originalPostId", createdPost.getOriginalPost().getId() );
	}

	@Test
	void findPostsWithPaginationShouldCallUseCase() {
		PostFilterDto filterDto = PostFilterDtoBuilder.buildFilter();
		when( findPostsWithPagination.execute( any() ) )
				.thenReturn( new Page<>( List.of( PostBuilder.buildCreatedPost() ), 1,1,1) );

		var page = adapter
				.findPostsWithPagination( filterDto,
						String.valueOf( USER_ID ) );

		verify( findPostsWithPagination )
				.execute( paginationFilterCaptor.capture() );

		assertThat( paginationFilterCaptor.getValue() )
				.hasFieldOrPropertyWithValue( "page", filterDto.getPage() )
				.hasFieldOrPropertyWithValue( "pageSize", filterDto.getPageSize() )
				.hasFieldOrPropertyWithValue( "filter.myPosts", filterDto.getMyPosts() )
				.hasFieldOrPropertyWithValue( "filter.beginDate", filterDto.getBeginDate() )
				.hasFieldOrPropertyWithValue( "filter.endDate", filterDto.getEndDate() )
				.hasFieldOrPropertyWithValue( "filter.userId", USER_ID);
	}

	@Test
	void findPostsWithPaginationWhenSuccessShouldReturnPosts() {
		PostFilterDto filterDto = PostFilterDtoBuilder.buildFilter();
		Post post = PostBuilder.buildCreatedPost();
		when( findPostsWithPagination.execute( any() ) )
				.thenReturn( new Page<>( List.of( post ), 1,1,1) );

		var page = adapter
				.findPostsWithPagination( filterDto,
						String.valueOf( USER_ID ) );

		verify( findPostsWithPagination )
				.execute( paginationFilterCaptor.capture() );

		assertThat( page.getData() )
				.first()
				.hasFieldOrPropertyWithValue( "content", post.getContent() )
				.hasFieldOrPropertyWithValue( "id", post.getId() )
				.hasFieldOrPropertyWithValue( "type", post.getType() );
	}
}