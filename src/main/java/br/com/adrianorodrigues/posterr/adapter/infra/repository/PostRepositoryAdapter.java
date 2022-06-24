package br.com.adrianorodrigues.posterr.adapter.infra.repository;

import java.util.Optional;

import br.com.adrianorodrigues.posterr.domain.Post;

public interface PostRepositoryAdapter {
	Optional<Post> findById(Post post);

	Post save(Post post);
}
