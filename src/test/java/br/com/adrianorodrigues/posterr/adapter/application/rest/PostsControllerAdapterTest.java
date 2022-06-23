package br.com.adrianorodrigues.posterr.adapter.application.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.helper.pool.application.rest.PostsDtoPool;
import br.com.adrianorodrigues.posterr.helper.pool.domain.PostsPool;
import br.com.adrianorodrigues.posterr.usecase.post.create.CreatePost;

@ExtendWith(MockitoExtension.class)
class PostsControllerAdapterTest {

	@Mock
	CreatePost createPost;
	@InjectMocks
	PostsControllerAdapter adapter;
	@Captor
	ArgumentCaptor<Post> postCaptor;

	@Test
	void createPostShouldCallUseCase() {
		Post createdPost = PostsPool.CREATED_POST;
		when(createPost.execute( any() ))
				.thenReturn( createdPost );

		adapter.createPost( PostsDtoPool.NEW_POST );

		verify( createPost )
				.execute( postCaptor.capture() );
		assertThat( postCaptor.getValue() )
				.hasFieldOrPropertyWithValue( "id", null )
				.hasFieldOrPropertyWithValue( "content", PostsDtoPool.NEW_POST.getContent() )
				.hasFieldOrPropertyWithValue( "type", PostsDtoPool.NEW_POST.getType() )
				.hasFieldOrPropertyWithValue( "originalPost", null );
	}

	@Test
	void createPostWhenPostHasOriginalPostShouldCallWithOriginalPost() {
		Post createdPost = PostsPool.CREATED_POST;
		when(createPost.execute( any() ))
				.thenReturn( createdPost );

		adapter.createPost( PostsDtoPool.NEW_POST_WITH_ORIGINAL_POST );

		verify( createPost )
				.execute( postCaptor.capture() );
		assertThat( postCaptor.getValue() )
				.hasFieldOrPropertyWithValue( "id", null )
				.hasFieldOrPropertyWithValue( "content", PostsDtoPool.NEW_POST_WITH_ORIGINAL_POST.getContent() )
				.hasFieldOrPropertyWithValue( "type", PostsDtoPool.NEW_POST_WITH_ORIGINAL_POST.getType() )
				.hasFieldOrPropertyWithValue( "originalPost.id", PostsDtoPool.NEW_POST_WITH_ORIGINAL_POST.getOriginalPostId() );
	}

	@Test
	void createPostShouldReturnPostData() {
		Post createdPost = PostsPool.NEW_POST_WITH_ORIGINAL_POST;
		when(createPost.execute( any() ))
				.thenReturn( createdPost );

		var post = adapter.createPost( PostsDtoPool.NEW_POST );

		assertThat( post )
				.hasFieldOrPropertyWithValue( "content", createdPost.getContent() )
				.hasFieldOrPropertyWithValue( "id",  createdPost.getId())
				.hasFieldOrPropertyWithValue( "type",  createdPost.getType())
				.hasFieldOrPropertyWithValue( "originalPostId",  createdPost.getOriginalPost().getId());
	}
}