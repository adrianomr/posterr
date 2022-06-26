package br.com.adrianorodrigues.posterr.usecase.post.find;

import br.com.adrianorodrigues.posterr.domain.Page;
import br.com.adrianorodrigues.posterr.domain.PaginationFilter;
import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.domain.PostFilter;

public interface FindPostsWithPagination {

	Page<Post> execute(PaginationFilter<PostFilter> filter);
}
