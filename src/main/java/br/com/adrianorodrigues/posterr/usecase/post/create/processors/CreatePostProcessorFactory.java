package br.com.adrianorodrigues.posterr.usecase.post.create.processors;

import br.com.adrianorodrigues.posterr.domain.Post;

public interface CreatePostProcessorFactory {

	CreatePostProcessor getProcessor(Post post);

}
