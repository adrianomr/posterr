package br.com.adrianorodrigues.posterr.adapter.infra.repository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.infra.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostRepositoryAdapterImpl implements PostRepositoryAdapter{

	private final PostRepository postRepository;

	@Override public Post save(Post post) {
		return postRepository.save( post );
	}
}
