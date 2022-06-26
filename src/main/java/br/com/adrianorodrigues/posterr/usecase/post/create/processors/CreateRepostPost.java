package br.com.adrianorodrigues.posterr.usecase.post.create.processors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import br.com.adrianorodrigues.posterr.adapter.infra.repository.PostRepositoryAdapter;
import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.enums.PostType;
import br.com.adrianorodrigues.posterr.exceptions.DataValidationException;

@Service
@RequiredArgsConstructor
public class CreateRepostPost implements CreatePostProcessor {

	private final PostRepositoryAdapter postRepositoryAdapter;

	@Override public Post execute(Post post) {
		var repostedPost = postRepositoryAdapter
				.findById( post.getOriginalPost() )
				.orElseThrow( () -> new DataValidationException( "originalPost", "Post must have a original post" ) );
		post.addOriginalPost( repostedPost );
		post.validateOriginalPost();
		return post;
	}

	@Override public PostType type() {
		return PostType.REPOST;
	}

}
