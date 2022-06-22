package br.com.adrianorodrigues.posterr.usecase;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import br.com.adrianorodrigues.posterr.adapter.infra.repository.PostRepositoryAdapter;
import br.com.adrianorodrigues.posterr.domain.Post;

@Service
@RequiredArgsConstructor
public class CreatePostImpl implements CreatePost {

	private final PostRepositoryAdapter repository;

	@Override
	public Post execute(Post post) {
		return repository.save( post );
	}
}
