package br.com.adrianorodrigues.posterr.application.rest;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.adrianorodrigues.posterr.adapter.application.rest.PostsControllerAdapter;
import br.com.adrianorodrigues.posterr.application.rest.dto.PostDto;

@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostsController {

	private final PostsControllerAdapter adapter;

	@PostMapping
	public PostDto createPost(PostDto post){
		return adapter.createPost( post );
	}

}
