package br.com.adrianorodrigues.posterr.application.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.adrianorodrigues.posterr.application.rest.dto.PostDto;

@RestController
@RequestMapping("posts")
public class PostsController {

	@PostMapping
	public PostDto createPost(PostDto post){
		return new PostDto();
	}

}
