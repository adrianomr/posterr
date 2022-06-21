package br.com.adrianorodrigues.posterr.infra.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.adrianorodrigues.posterr.domain.Post;

public interface PostRepository extends CrudRepository<Post, UUID> {
}
