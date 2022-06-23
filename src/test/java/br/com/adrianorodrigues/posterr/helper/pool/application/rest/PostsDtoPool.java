package br.com.adrianorodrigues.posterr.helper.pool.application.rest;

import java.util.UUID;

import br.com.adrianorodrigues.posterr.application.rest.dto.PostDto;
import br.com.adrianorodrigues.posterr.enums.PostType;

public class PostsDtoPool {
	public static final PostDto NEW_POST = PostDto.builder()
			.content( "CREATED POST" )
			.type( PostType.REGULAR )
			.build();
	public static final PostDto NEW_QUOTE_POST_WITHOUT_ORIGINAL_POST = PostDto.builder()
			.content( "CREATED POST" )
			.type( PostType.QUOTE )
			.build();
	public static final PostDto NEW_REPOST_POST_WITHOUT_ORIGINAL_POST = PostDto.builder()
			.content( "CREATED POST" )
			.type( PostType.REPOST )
			.build();
	public static final PostDto CREATED_POST = PostDto.builder()
			.id( UUID.randomUUID() )
			.content( "CREATED POST" )
			.type( PostType.REGULAR )
			.originalPostId( UUID.randomUUID() )
			.build();
	public static final PostDto NEW_POST_WITH_ORIGINAL_POST = PostDto.builder()
			.content( "CREATED POST" )
			.type( PostType.REGULAR )
			.originalPostId( UUID.randomUUID() )
			.build();

}
