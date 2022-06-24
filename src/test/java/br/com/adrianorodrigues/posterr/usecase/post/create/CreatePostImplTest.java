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
import br.com.adrianorodrigues.posterr.util.pool.domain.PostsPool;
import br.com.adrianorodrigues.posterr.usecase.post.create.processors.CreatePostProcessor;
import br.com.adrianorodrigues.posterr.usecase.post.create.processors.CreatePostProcessorFactory;

@ExtendWith(MockitoExtension.class)
class CreatePostImplTest {

	@Mock
	private CreatePostProcessorFactory processorFactory;
	@Mock
	private CreatePostProcessor processor;
	@Mock
	private PostRepositoryAdapter postRepositoryAdapter;
	@InjectMocks
	private CreatePostImpl createPost;

	@BeforeEach
	void setUp() {
		when( processorFactory.getProcessor( PostsPool.NEW_POST ) )
				.thenReturn( processor );
		when( processor.execute( PostsPool.NEW_POST ) )
				.thenReturn( PostsPool.NEW_POST );
		when( postRepositoryAdapter.save( PostsPool.NEW_POST ) )
				.thenReturn( PostsPool.CREATED_POST );
	}

	@Test
	void executeWhenProcessorExecutedShouldReturnPost() {
		var post = createPost.execute( PostsPool.NEW_POST );

		assertThat( post )
				.hasFieldOrPropertyWithValue( "id", PostsPool.CREATED_POST.getId() )
				.hasFieldOrPropertyWithValue( "content", PostsPool.CREATED_POST.getContent() );
	}

	@Test
	void executeWhenProcessorFoundShouldCallProcessor() {
		var post = createPost.execute( PostsPool.NEW_POST );

		verify( processor ).execute( PostsPool.NEW_POST );
	}

	@Test
	void executeWhenProcessorExecutedShouldSavePost() {
		var post = createPost.execute( PostsPool.NEW_POST );

		verify( postRepositoryAdapter ).save( PostsPool.NEW_POST );
	}

}