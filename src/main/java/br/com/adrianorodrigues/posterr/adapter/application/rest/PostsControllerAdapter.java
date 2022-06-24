package br.com.adrianorodrigues.posterr.adapter.application.rest;

import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import br.com.adrianorodrigues.posterr.application.rest.dto.PostDto;
import br.com.adrianorodrigues.posterr.mapper.application.rest.PostDtoMapper;
import br.com.adrianorodrigues.posterr.mapper.domain.PostMapper;
import br.com.adrianorodrigues.posterr.usecase.post.create.CreatePost;

@Service
@RequiredArgsConstructor
public class PostsControllerAdapter {

	private final CreatePost createPost;

	public PostDto createPost(PostDto postDto, String userId) {
		var newPost = PostMapper.INSTANCE.map( postDto );
		newPost.addOriginalPost( postDto.getOriginalPostId() );
		newPost.setUserId( UUID.fromString( userId ) );

		var post = createPost.execute( newPost );

		return PostDtoMapper.INSTANCE.map( post );
	}

}
