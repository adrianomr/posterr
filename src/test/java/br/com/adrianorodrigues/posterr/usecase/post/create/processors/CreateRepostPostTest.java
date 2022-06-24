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
import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.enums.PostType;
import br.com.adrianorodrigues.posterr.exceptions.DataValidationException;
import br.com.adrianorodrigues.posterr.util.builder.domain.PostBuilder;

@ExtendWith(MockitoExtension.class)
class CreateRepostPostTest {

	public static final Post CREATED_POST = PostBuilder.buildCreatedPost();
	@Mock
	PostRepositoryAdapter postRepositoryAdapter;
	@InjectMocks
	CreateRepostPost createRepostPost;

	@Test
	void executeWhenSuccessShouldCreatePost() {
		var newQuotePost = PostBuilder.buildNewRepostPost();
		when( postRepositoryAdapter.findById( newQuotePost.getOriginalPost() ) )
				.thenReturn( Optional.of( CREATED_POST ) );

		var post = createRepostPost.execute( newQuotePost );

		assertThat( post )
				.hasFieldOrPropertyWithValue( "content", newQuotePost.getContent() )
				.hasFieldOrPropertyWithValue( "type", newQuotePost.getType() )
				.hasFieldOrPropertyWithValue( "originalPost", CREATED_POST );
	}

	@Test
	void executeWhenOriginalPostNotSentShouldThrowException() {
		var newQuotePost = PostBuilder.buildNewRepostPostWithoutOriginal();
		when( postRepositoryAdapter.findById( null ) )
				.thenReturn( Optional.empty() );

		assertThrows( DataValidationException.class, () -> createRepostPost.execute( newQuotePost ) );
	}

	@Test
	void executeWhenOriginalPostIsQuotePostShouldThrowException() {
		var newQuotePost = PostBuilder.buildNewRepostPostReferencingRepostPost();
		when( postRepositoryAdapter.findById( newQuotePost.getOriginalPost() ) )
				.thenReturn( Optional.of( PostBuilder.buildCreatedRepostPost() ) );

		assertThrows( DataValidationException.class, () -> createRepostPost.execute( newQuotePost ) );
	}

	@Test
	void type() {
		assertEquals( PostType.REPOST, createRepostPost.type() );
	}
}