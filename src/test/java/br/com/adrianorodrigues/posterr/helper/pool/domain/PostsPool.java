package br.com.adrianorodrigues.posterr.helper.pool.domain;

import java.util.UUID;

import br.com.adrianorodrigues.posterr.domain.Post;

public class PostsPool {

	public static Post NEW_POST = Post.builder()
			.content( "CREATED POST" )
			.build();

	public static Post CREATED_POST = Post.builder()
			.id( UUID.randomUUID() )
			.content( "CREATED POST" )
			.build();

}
