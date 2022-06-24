package br.com.adrianorodrigues.posterr.adapter.infra.repository;

import java.util.Optional;
import java.util.UUID;

import br.com.adrianorodrigues.posterr.domain.User;

public interface UserRepositoryAdapter {

	Optional<User> findUserById(UUID id);

	void save(User user);
}
