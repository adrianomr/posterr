package br.com.adrianorodrigues.posterr.usecase.post.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.adrianorodrigues.posterr.adapter.infra.repository.PostRepositoryAdapter;
import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.usecase.user.UpdateUser;
import br.com.adrianorodrigues.posterr.util.builder.domain.PostBuilder;
import br.com.adrianorodrigues.posterr.usecase.post.create.processors.CreatePostProcessor;
import br.com.adrianorodrigues.posterr.usecase.post.create.processors.CreatePostProcessorFactory;

@ExtendWith(MockitoExtension.class)
class CreatePostImplTest {

	public static final Post POST = PostBuilder.buildNewPost();
	public static final Post CREATED_POST = PostBuilder.buildCreatedPost();
	@Mock
	private CreatePostProcessorFactory processorFactory;
	@Mock
	private CreatePostProcessor processor;
	@Mock
	private PostRepositoryAdapter postRepositoryAdapter;
	@Mock
	private UpdateUser updateUser;
	@InjectMocks
	private CreatePostImpl createPost;

	@BeforeEach
	void setUp() {
		var post = POST;
		when( processorFactory.getProcessor( post ) )
				.thenReturn( processor );
		when( processor.execute( post ) )
				.thenReturn( post );
		when( postRepositoryAdapter.save( post ) )
				.thenReturn( CREATED_POST );
	}

	@Test
	void executeWhenProcessorExecutedShouldReturnPost() {
		var post = createPost.execute( POST );

		assertThat( post )
				.hasFieldOrPropertyWithValue( "id", CREATED_POST.getId() )
				.hasFieldOrPropertyWithValue( "content", CREATED_POST.getContent() );
	}

	@Test
	void executeWhenProcessorFoundShouldCallProcessor() {
		var newPost = POST;

		createPost.execute( newPost );

		verify( processor ).execute( newPost );
	}

	@Test
	void executeWhenProcessorExecutedShouldSavePost() {
		var newPost = POST;

		createPost.execute( newPost );

		verify( postRepositoryAdapter ).save( newPost );
	}

	@Test
	void executeWhenProcessorExecutedShouldUpdateUser() {
		var newPost = POST;

		createPost.execute( newPost );

		verify( updateUser ).execute( newPost );
	}

}