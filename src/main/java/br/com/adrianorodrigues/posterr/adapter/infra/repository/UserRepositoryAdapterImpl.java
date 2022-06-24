package br.com.adrianorodrigues.posterr.adapter.infra.repository;

import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adrianorodrigues.posterr.domain.User;
import br.com.adrianorodrigues.posterr.infra.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserRepositoryAdapterImpl implements UserRepositoryAdapter{

	@Autowired
	UserRepository userRepository;

	@Override
	public Optional<User> findUserById(UUID id) {
		return userRepository.findById( id );
	}

	@Override
	public void save(User user) {
		userRepository.save( user );
	}
}
