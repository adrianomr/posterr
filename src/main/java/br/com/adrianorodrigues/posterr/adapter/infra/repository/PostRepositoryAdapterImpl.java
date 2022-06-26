package br.com.adrianorodrigues.posterr.adapter.infra.repository;

import static java.util.Objects.isNull;

import java.time.Instant;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.adrianorodrigues.posterr.domain.Page;
import br.com.adrianorodrigues.posterr.domain.PaginationFilter;
import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.domain.PostFilter;
import br.com.adrianorodrigues.posterr.infra.repository.PostRepository;
import br.com.adrianorodrigues.posterr.infra.repository.dto.PostsFilterDto;

@Service
@RequiredArgsConstructor
public class PostRepositoryAdapterImpl implements PostRepositoryAdapter {

	private final PostRepository repository;

	@Override
	public Optional<Post> findById(Post post) {
		if (isNull( post )) {
			return Optional.empty();
		}
		return repository
				.findById( post.getId() );
	}

	@Override
	public Post save(Post post) {
		post.setCreatedAt( Instant.now() );
		return repository.save( post );
	}

	@Override public Page<Post> findAllPostsPaginated(PaginationFilter<PostFilter> paginationFilter) {
		var pageable = buildPage( paginationFilter );
		var page = repository.findAllPaginatedPosts( buildFilter( paginationFilter.getFilter() ), pageable );
		return buildPage( pageable, page );
	}

	private Page<Post> buildPage(Pageable pageable, org.springframework.data.domain.Page<Post> page) {
		return Page.<Post> builder()
				.elements( page.stream().toList() )
				.pageSize( 10 )
				.currentPage( pageable.getPageNumber() )
				.totalPages( page.getTotalPages() )
				.build();
	}

	private Pageable buildPage(PaginationFilter<PostFilter> paginationFilter) {
		return PageRequest.of( paginationFilter.getPage() - 1, paginationFilter.getPageSize(),
						Sort.by( Sort.Direction.DESC, "createdAt" ) )
				.toOptional()
				.orElse( Pageable.ofSize( paginationFilter.getPageSize() ).first() );
	}

	private PostsFilterDto buildFilter(PostFilter filter) {
		return PostsFilterDto.builder()
				.beginDate( filter.getBeginDate() )
				.endDate( filter.getEndDate() )
				.userId( filter.getUserId() )
				.build();
	}
}
