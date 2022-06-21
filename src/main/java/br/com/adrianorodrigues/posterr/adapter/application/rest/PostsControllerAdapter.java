package br.com.adrianorodrigues.posterr.adapter.application.rest;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import br.com.adrianorodrigues.posterr.application.rest.dto.PostDto;
import br.com.adrianorodrigues.posterr.mapper.application.rest.PostDtoMapper;
import br.com.adrianorodrigues.posterr.mapper.domain.PostMapper;
import br.com.adrianorodrigues.posterr.usecase.CreatePost;

@Service
@RequiredArgsConstructor
public class PostsControllerAdapter {

	private final CreatePost createPost;

	public PostDto createPost(PostDto postDto){
		var newPost = PostMapper.INSTANCE.map( postDto );

		var post = createPost.execute( newPost );

		return PostDtoMapper.INSTANCE.map( post );
	}

}
