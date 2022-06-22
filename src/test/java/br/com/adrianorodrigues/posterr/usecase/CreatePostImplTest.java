package br.com.adrianorodrigues.posterr.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.adrianorodrigues.posterr.adapter.infra.repository.PostRepositoryAdapter;
import br.com.adrianorodrigues.posterr.helper.pool.domain.PostsPool;
import br.com.adrianorodrigues.posterr.infra.repository.PostRepository;

@ExtendWith(MockitoExtension.class)
class CreatePostImplTest {

	@Mock
	private PostRepositoryAdapter repositoryAdapter;
	@InjectMocks
	private CreatePostImpl createPost;

	@Test
	void executeWhenSavedShouldReturnPost() {
		when( repositoryAdapter.save( PostsPool.NEW_POST ) )
				.thenReturn( PostsPool.CREATED_POST );

		var post = createPost.execute( PostsPool.NEW_POST );

		assertThat( post )
				.hasFieldOrPropertyWithValue( "id", PostsPool.CREATED_POST.getId() )
				.hasFieldOrPropertyWithValue( "content", PostsPool.CREATED_POST.getContent() );
	}

	@Test
	void executeShouldSavePost() {
		when( repositoryAdapter.save( PostsPool.NEW_POST ) )
				.thenReturn( PostsPool.CREATED_POST );

		var post = createPost.execute( PostsPool.NEW_POST );

		verify( repositoryAdapter ).save( PostsPool.NEW_POST );
	}
}