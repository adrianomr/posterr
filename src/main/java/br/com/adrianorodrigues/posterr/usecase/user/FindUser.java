package br.com.adrianorodrigues.posterr.usecase.user;

import java.util.UUID;

import br.com.adrianorodrigues.posterr.domain.User;

public interface FindUser {

	User execute(UUID userId);

}
