package br.com.adrianorodrigues.posterr.usecase.post.create.processors;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.com.adrianorodrigues.posterr.enums.PostType;
import br.com.adrianorodrigues.posterr.exceptions.DataValidationException;
import br.com.adrianorodrigues.posterr.util.builder.domain.PostBuilder;

class CreateRegularPostTest {

	private CreateRegularPost createRegularPost = new CreateRegularPost();

	@Test
	void executeWhenSuccessShouldReturnPost() {
		var newPost = PostBuilder.buildNewPost();
		var post = createRegularPost.execute( newPost );

		assertEquals( newPost, post );
	}

	@Test
	void executeWhenHasOriginalPostShouldThrowException() {
		var post = PostBuilder.buildNewPostWithOriginalPost();
		assertThrows( DataValidationException.class,
				() -> createRegularPost.execute( post ) );
	}

	@Test
	void type() {
		assertEquals( PostType.REGULAR, createRegularPost.type() );
	}
}