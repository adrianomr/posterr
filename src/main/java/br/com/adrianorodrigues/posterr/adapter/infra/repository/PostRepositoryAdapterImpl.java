package br.com.adrianorodrigues.posterr.adapter.infra.repository;

import static java.util.Objects.isNull;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.infra.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostRepositoryAdapterImpl implements PostRepositoryAdapter {

	private final PostRepository postRepository;

	@Override
	public Optional<Post> findById(Post post) {
		if( isNull(post) )
			return Optional.empty();
		return postRepository
				.findById( post.getId() );
	}

	@Override
	public Post save(Post post) {
		return postRepository.save( post );
	}
}
