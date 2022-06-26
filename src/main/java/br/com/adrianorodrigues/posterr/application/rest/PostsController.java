package br.com.adrianorodrigues.posterr.application.rest;

import java.time.Instant;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.adrianorodrigues.posterr.adapter.application.rest.PostsControllerAdapter;
import br.com.adrianorodrigues.posterr.application.rest.config.RestContext;
import br.com.adrianorodrigues.posterr.application.rest.dto.PageDto;
import br.com.adrianorodrigues.posterr.application.rest.dto.PostDto;
import br.com.adrianorodrigues.posterr.application.rest.dto.PostFilterDto;

@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostsController {

	private final PostsControllerAdapter adapter;
	private final RestContext restContext;

	@PostMapping
	public PostDto createPost(@RequestBody PostDto post){
		var userId = restContext.getUserId();
		return adapter.createPost( post, userId );
	}

	@GetMapping
	public PageDto<PostDto> getPostsPaginated(@RequestParam(required = false) Instant beginDate,
			@RequestParam(required = false) Instant endDate,
			@RequestParam(required = false, defaultValue = "false") Boolean myPosts,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize){
		var userId = restContext.getUserId();
		var filter = PostFilterDto.builder()
				.myPosts( myPosts )
				.pageSize( pageSize )
				.page( page )
				.beginDate( beginDate )
				.endDate( endDate )
				.build();
		return adapter.findPostsWithPagination( filter , userId );
	}

}
