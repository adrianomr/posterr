package br.com.adrianorodrigues.posterr.usecase.user;

import br.com.adrianorodrigues.posterr.domain.Post;

public interface UpdateUser {
	void execute(Post newPost);
}
