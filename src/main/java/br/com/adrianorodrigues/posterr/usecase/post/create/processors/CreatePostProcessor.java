package br.com.adrianorodrigues.posterr.usecase.post.create.processors;

import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.enums.PostType;

public interface CreatePostProcessor {

	Post execute(Post post);

	PostType type();

}
