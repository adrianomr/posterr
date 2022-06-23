package br.com.adrianorodrigues.posterr.usecase.post.create.processors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import br.com.adrianorodrigues.posterr.adapter.infra.repository.PostRepositoryAdapter;
import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.enums.PostType;

@Service
@RequiredArgsConstructor
public class CreateQuotePost implements CreatePostProcessor {

	private final PostRepositoryAdapter postRepositoryAdapter;

	@Override public Post execute(Post post) {
		var quotedPost = postRepositoryAdapter
				.findById( post.getOriginalPost() )
				.orElse( null );
		post.setOriginalPost( quotedPost );
		post.validateOriginalPost();
		return post;
	}

	@Override public PostType type() {
		return PostType.QUOTE;
	}

}
