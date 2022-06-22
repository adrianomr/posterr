package br.com.adrianorodrigues.posterr.adapter.infra.repository;

import br.com.adrianorodrigues.posterr.domain.Post;

public interface PostRepositoryAdapter {
	Post save(Post post);
}
