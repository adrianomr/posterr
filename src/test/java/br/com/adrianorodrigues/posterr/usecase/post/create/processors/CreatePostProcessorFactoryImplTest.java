package br.com.adrianorodrigues.posterr.usecase.post.create.processors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.adrianorodrigues.posterr.adapter.infra.repository.PostRepositoryAdapter;
import br.com.adrianorodrigues.posterr.util.pool.domain.PostsPool;

@SpringBootTest(classes = { CreatePostProcessorFactoryImpl.class, CreateRegularPost.class, CreateQuotePost.class,
		CreateRepostPost.class })
class CreatePostProcessorFactoryImplTest {

	@MockBean
	PostRepositoryAdapter postRepositoryAdapter;
	@Autowired
	private CreateRegularPost createRegularPost;
	@Autowired
	private CreateQuotePost createQuotePost;
	@Autowired
	private CreateRepostPost createRepostPost;
	@Autowired
	private CreatePostProcessorFactoryImpl factory;

	@Test
	void getProcessorWhenPostIsRegularShouldReturnRegularPostProcessor() {
		var processor = factory.getProcessor( PostsPool.NEW_POST );

		assertEquals( createRegularPost, processor );
	}

	@Test
	void getProcessorWhenPostIsQuoteShouldReturnQuotePostProcessor() {
		var processor = factory.getProcessor( PostsPool.NEW_QUOTE_POST );

		assertEquals( createQuotePost, processor );
	}

	@Test
	void getProcessorWhenPostIsRepostShouldReturnQuotePostProcessor() {
		var processor = factory.getProcessor( PostsPool.NEW_REPOST_POST );

		assertEquals( createRepostPost, processor );
	}
}