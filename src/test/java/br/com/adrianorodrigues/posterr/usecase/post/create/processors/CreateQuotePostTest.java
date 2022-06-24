package br.com.adrianorodrigues.posterr.usecase.post.create.processors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.adrianorodrigues.posterr.adapter.infra.repository.PostRepositoryAdapter;
import br.com.adrianorodrigues.posterr.enums.PostType;
import br.com.adrianorodrigues.posterr.exceptions.DataValidationException;
import br.com.adrianorodrigues.posterr.util.pool.domain.PostsPool;

@ExtendWith(MockitoExtension.class)
class CreateQuotePostTest {

	@Mock
	PostRepositoryAdapter postRepositoryAdapter;
	@InjectMocks
	CreateQuotePost createQuotePost;

	@Test
	void executeWhenSuccessShouldCreatePost() {
		var newQuotePost = PostsPool.NEW_QUOTE_POST;
		when( postRepositoryAdapter.findById( PostsPool.CREATED_POST ) )
				.thenReturn( Optional.of( PostsPool.CREATED_POST ) );

		var post = createQuotePost.execute( newQuotePost );

		assertThat( post )
				.hasFieldOrPropertyWithValue( "content", newQuotePost.getContent() )
				.hasFieldOrPropertyWithValue( "type", newQuotePost.getType() )
				.hasFieldOrPropertyWithValue( "originalPost", PostsPool.CREATED_POST );
	}

	@Test
	void executeWhenOriginalPostNotSentShouldThrowException() {
		var newQuotePost = PostsPool.NEW_QUOTE_POST_WITHOUT_ORIGINAL;
		when( postRepositoryAdapter.findById( null ) )
				.thenReturn( Optional.empty() );

		assertThrows( DataValidationException.class, () -> createQuotePost.execute( newQuotePost ) );
	}

	@Test
	void executeWhenOriginalPostIsQuotePostShouldThrowException() {
		var newQuotePost = PostsPool.NEW_QUOTE_POST_QUOTING_QUOTE_POST;
		when( postRepositoryAdapter.findById( PostsPool.CREATED_QUOTE_POST ) )
				.thenReturn( Optional.of( PostsPool.CREATED_QUOTE_POST ) );

		assertThrows( DataValidationException.class, () -> createQuotePost.execute( newQuotePost ) );
	}

	@Test
	void type() {
		assertEquals( PostType.QUOTE, createQuotePost.type() );
	}
}