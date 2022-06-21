package br.com.adrianorodrigues.posterr.usecase;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.infra.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class CreatePostImpl implements CreatePost {

	private final PostRepository repository;

	@Override
	public Post execute(Post post) {
		return repository.save( post );
	}
}
