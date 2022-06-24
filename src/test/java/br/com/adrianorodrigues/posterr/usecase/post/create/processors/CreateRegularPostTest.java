package br.com.adrianorodrigues.posterr.usecase.post.create.processors;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.com.adrianorodrigues.posterr.enums.PostType;
import br.com.adrianorodrigues.posterr.exceptions.DataValidationException;
import br.com.adrianorodrigues.posterr.util.pool.domain.PostsPool;

class CreateRegularPostTest {

	private CreateRegularPost createRegularPost = new CreateRegularPost();

	@Test
	void executeWhenSuccessShouldReturnPost() {
		var post = createRegularPost.execute( PostsPool.NEW_POST );

		assertEquals( PostsPool.NEW_POST, post );
	}

	@Test
	void executeWhenHasOriginalPostShouldThrowException() {
		assertThrows( DataValidationException.class,
				() -> createRegularPost.execute( PostsPool.NEW_POST_WITH_ORIGINAL_POST ) );
	}

	@Test
	void type() {
		assertEquals( PostType.REGULAR, createRegularPost.type() );
	}
}