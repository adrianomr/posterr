package br.com.adrianorodrigues.posterr.usecase.post.create.processors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.enums.PostType;

@Service
@RequiredArgsConstructor
public class CreateRegularPost implements CreatePostProcessor {

	@Override public Post execute(Post post) {
		post.validateRegularPost();
		return post;
	}

	@Override public PostType type() {
		return PostType.REGULAR;
	}

}
