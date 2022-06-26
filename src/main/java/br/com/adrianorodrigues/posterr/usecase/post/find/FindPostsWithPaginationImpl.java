package br.com.adrianorodrigues.posterr.usecase.post.find;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import br.com.adrianorodrigues.posterr.adapter.infra.repository.PostRepositoryAdapter;
import br.com.adrianorodrigues.posterr.domain.Page;
import br.com.adrianorodrigues.posterr.domain.PaginationFilter;
import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.domain.PostFilter;

@Service
@RequiredArgsConstructor
public class FindPostsWithPaginationImpl implements FindPostsWithPagination {

	private final PostRepositoryAdapter postRepositoryAdapter;

	@Override
	public Page<Post> execute(PaginationFilter<PostFilter> filter) {
		filter.getFilter().prepare();
		return postRepositoryAdapter.findAllPostsPaginated( filter );
	}
}
