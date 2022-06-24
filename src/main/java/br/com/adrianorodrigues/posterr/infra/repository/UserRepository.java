package br.com.adrianorodrigues.posterr.infra.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.adrianorodrigues.posterr.domain.User;

public interface UserRepository extends CrudRepository<User, UUID> {
}
