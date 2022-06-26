package br.com.adrianorodrigues.posterr.adapter.application.rest;

import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import br.com.adrianorodrigues.posterr.application.rest.dto.PageDto;
import br.com.adrianorodrigues.posterr.application.rest.dto.PostDto;
import br.com.adrianorodrigues.posterr.application.rest.dto.PostFilterDto;
import br.com.adrianorodrigues.posterr.domain.PaginationFilter;
import br.com.adrianorodrigues.posterr.mapper.application.rest.PostDtoMapper;
import br.com.adrianorodrigues.posterr.mapper.application.rest.PostFilterDtoMapper;
import br.com.adrianorodrigues.posterr.mapper.domain.PostMapper;
import br.com.adrianorodrigues.posterr.usecase.post.create.CreatePost;
import br.com.adrianorodrigues.posterr.usecase.post.find.FindPostsWithPagination;

@Service
@RequiredArgsConstructor
public class PostsControllerAdapter {

	private final CreatePost createPost;
	private final FindPostsWithPagination findPostsWithPagination;

	public PostDto createPost(PostDto postDto, String userId) {
		var newPost = PostMapper.INSTANCE.map( postDto );
		newPost.addOriginalPost( postDto.getOriginalPostId() );
		newPost.setUserId( UUID.fromString( userId ) );

		var post = createPost.execute( newPost );

		return PostDtoMapper.INSTANCE.map( post );
	}

	public PageDto<PostDto> findPostsWithPagination(PostFilterDto filterDto, String userId) {
		var filter = PostFilterDtoMapper.INSTANCE.map( filterDto );
		filter.setUserId( UUID.fromString( userId ) );
		var paginationFilter = new PaginationFilter<>( filterDto.getPage(), filterDto.getPageSize(), filter );
		var page = findPostsWithPagination.execute( paginationFilter );
		var posts = page.getElements().stream().map( PostDtoMapper.INSTANCE::map ).toList();
		return PageDto.<PostDto>builder()
				.page( filterDto.getPage() )
				.pageSize( filterDto.getPageSize() )
				.totalPages( page.getTotalPages() )
				.totalElements( posts.size() )
				.data( posts )
				.build();
	}

}
