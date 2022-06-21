package br.com.adrianorodrigues.posterr.helper.pool.application.rest;

import java.util.UUID;

import br.com.adrianorodrigues.posterr.application.rest.dto.PostDto;

public class PostsDtoPool {
	public static PostDto NEW_POST = PostDto.builder()
			.content( "CREATED POST" )
			.build();
	public static PostDto CREATED_POST = PostDto.builder()
			.id( UUID.randomUUID() )
			.content( "CREATED POST" )
			.build();
}
