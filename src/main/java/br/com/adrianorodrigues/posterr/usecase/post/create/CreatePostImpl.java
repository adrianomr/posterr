package br.com.adrianorodrigues.posterr.usecase.post.create;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.adrianorodrigues.posterr.adapter.infra.repository.PostRepositoryAdapter;
import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.usecase.post.create.processors.CreatePostProcessorFactory;
import br.com.adrianorodrigues.posterr.usecase.user.UpdateUser;

@Service
@RequiredArgsConstructor
public class CreatePostImpl implements CreatePost {

	private final CreatePostProcessorFactory processorFactory;
	private final PostRepositoryAdapter postRepositoryAdapter;
	private final UpdateUser updateUser;

	@Transactional
	@Override
	public Post execute(Post newPost) {
		newPost.validate();

		updateUser.execute(newPost);

		Post post = processorFactory
				.getProcessor( newPost )
				.execute( newPost );

		return postRepositoryAdapter.save( post );
	}
}
