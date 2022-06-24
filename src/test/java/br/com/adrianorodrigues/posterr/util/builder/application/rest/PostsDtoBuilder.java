package br.com.adrianorodrigues.posterr.util.builder.application.rest;

import java.util.UUID;

import br.com.adrianorodrigues.posterr.application.rest.dto.PostDto;
import br.com.adrianorodrigues.posterr.enums.PostType;

public class PostsDtoBuilder {
	public static PostDto buildNewPost() {
		return PostDto.builder()
				.content( "CREATED POST" )
				.type( PostType.REGULAR )
				.build();
	}

	public static PostDto buildNewQuotePostWithoutOriginalPost() {
		return PostDto.builder()
				.content( "CREATED POST" )
				.type( PostType.QUOTE )
				.build();
	}

	public static PostDto buildNewRepostPostWithoutOriginalPost() {
		return PostDto.builder()
				.content( "CREATED POST" )
				.type( PostType.REPOST )
				.build();
	}

	public static PostDto buildCreatedPost() {
		return PostDto.builder()
				.id( UUID.randomUUID() )
				.content( "CREATED POST" )
				.type( PostType.REGULAR )
				.originalPostId( UUID.randomUUID() )
				.build();
	}

	public static PostDto buildNewPostWithOriginalPost() {
		return PostDto.builder()
				.content( "CREATED POST" )
				.type( PostType.REGULAR )
				.originalPostId( UUID.randomUUID() )
				.build();
	}

}
