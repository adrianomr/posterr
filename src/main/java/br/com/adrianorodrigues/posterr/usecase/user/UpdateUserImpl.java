package br.com.adrianorodrigues.posterr.usecase.user;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import br.com.adrianorodrigues.posterr.adapter.infra.repository.UserRepositoryAdapter;
import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.exceptions.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class UpdateUserImpl implements UpdateUser {

	private final UserRepositoryAdapter userRepositoryAdapter;

	@Override
	public void execute(Post newPost) {
		var user = userRepositoryAdapter
				.findUserById( newPost.getUserId() )
				.orElseThrow( () -> new ResourceNotFoundException( "user", newPost.getUserId().toString() ) );

		user.addPost();

		userRepositoryAdapter.save( user );
	}
}
