package br.com.adrianorodrigues.posterr.adapter.infra.repository;

import java.util.Optional;

import br.com.adrianorodrigues.posterr.domain.Page;
import br.com.adrianorodrigues.posterr.domain.PaginationFilter;
import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.domain.PostFilter;

public interface PostRepositoryAdapter {
	Optional<Post> findById(Post post);

	Post save(Post post);

	Page<Post> findAllPostsPaginated(PaginationFilter<PostFilter> paginationFilter);
}
