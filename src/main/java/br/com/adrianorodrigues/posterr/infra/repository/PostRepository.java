package br.com.adrianorodrigues.posterr.infra.repository;

import static java.util.Objects.isNull;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.infra.repository.dto.PostsFilterDto;

public interface PostRepository extends CrudRepository<Post, UUID>, JpaSpecificationExecutor<Post> {

	default Page<Post> findAllPaginatedPosts(PostsFilterDto filter, Pageable pageable) {
		var spec = withCreatedAtLessThanOrEqualTo( filter.getEndDate() )
				.and( withCreatedAtGreaterThanOrEqualTo( filter.getBeginDate() ) )
				.and( withUserIdEqualTo( filter.getUserId() ) );

		return findAll( spec, pageable );
	}

	static Specification<Post> withCreatedAtLessThanOrEqualTo(Instant date) {
		Specification<Post> spec = (root, query, cb) -> cb.lessThanOrEqualTo( root.get( "createdAt" ), date );
		return buildSpec( spec, date );
	}

	static Specification<Post> withCreatedAtGreaterThanOrEqualTo(Instant date) {
		Specification<Post> spec = (root, query, cb) -> cb.greaterThanOrEqualTo( root.get( "createdAt" ), date );
		return buildSpec( spec, date );
	}

	static Specification<Post> withUserIdEqualTo(UUID userId) {
		Specification<Post> spec = (root, query, cb) -> cb.equal( root.get( "userId" ), userId );
		return buildSpec( spec, userId );
	}

	static Specification<Post> buildSpec(Specification<Post> predicate, Object value) {
		if (isNull( value )) {
			return noFilter();
		}
		return predicate;
	}

	private static Specification<Post> noFilter() {
		return (root, query, cb) -> null;
	}

}
