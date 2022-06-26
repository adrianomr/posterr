package br.com.adrianorodrigues.posterr.usecase.user;

import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import br.com.adrianorodrigues.posterr.adapter.infra.repository.UserRepositoryAdapter;
import br.com.adrianorodrigues.posterr.domain.User;
import br.com.adrianorodrigues.posterr.exceptions.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class FindUserImpl implements FindUser{

	private final UserRepositoryAdapter userRepositoryAdapter;

	@Override public User execute(UUID userId) {
		var user = userRepositoryAdapter.findUserById( userId )
				.orElseThrow(() -> new ResourceNotFoundException( "user", userId.toString() ) );
		return user.updateTotals();
	}

}
