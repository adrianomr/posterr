package br.com.adrianorodrigues.posterr.adapter.infra.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.helper.context.AbstractContextMockDataBase;
import br.com.adrianorodrigues.posterr.helper.pool.domain.PostsPool;
import br.com.adrianorodrigues.posterr.infra.repository.PostRepository;

@SpringBootTest
class PostRepositoryAdapterImplTest extends AbstractContextMockDataBase {

	@Autowired
	private PostRepository repository;
	@Autowired
	private PostRepositoryAdapter postRepositoryAdapter;

	@AfterEach
	void tearDown() {
		repository.deleteAll();
	}

	@Test
	void saveShouldSavePost() {
		var post = postRepositoryAdapter.save( PostsPool.NEW_POST );

		var postFromDb = repository.findById( post.getId() )
				.orElse( new Post() );
		assertThat( post )
				.hasFieldOrPropertyWithValue( "id", postFromDb.getId() )
				.hasFieldOrPropertyWithValue( "content", postFromDb.getContent() );
	}
}