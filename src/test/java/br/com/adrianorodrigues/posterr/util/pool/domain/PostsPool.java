package br.com.adrianorodrigues.posterr.util.pool.domain;

import java.util.UUID;

import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.enums.PostType;

public class PostsPool {

	public static final Post NEW_POST = Post.builder()
			.content( "CREATED POST" )
			.type( PostType.REGULAR )
			.build();

	public static final Post NEW_QUOTE_POST_WITHOUT_ORIGINAL = Post.builder()
			.content( "CREATED POST" )
			.type( PostType.QUOTE )
			.build();

	public static final Post NEW_REPOST_POST_WITHOUT_ORIGINAL = Post.builder()
			.content( "CREATED POST" )
			.type( PostType.REPOST )
			.build();

	public static final Post CREATED_POST = Post.builder()
			.id( UUID.randomUUID() )
			.content( "CREATED POST" )
			.type( PostType.REGULAR )
			.build();

	public static final Post CREATED_QUOTE_POST = Post.builder()
			.id( UUID.randomUUID() )
			.content( "CREATED POST" )
			.type( PostType.QUOTE )
			.originalPost( CREATED_POST )
			.build();

	public static final Post CREATED_REPOST_POST = Post.builder()
			.id( UUID.randomUUID() )
			.content( "CREATED POST" )
			.type( PostType.REPOST )
			.originalPost( CREATED_POST )
			.build();

	public static final Post NEW_QUOTE_POST = Post.builder()
			.content( "CREATED POST" )
			.type( PostType.QUOTE )
			.originalPost( CREATED_POST )
			.build();

	public static final Post NEW_QUOTE_POST_QUOTING_QUOTE_POST = Post.builder()
			.content( "CREATED POST" )
			.type( PostType.QUOTE )
			.originalPost( CREATED_QUOTE_POST )
			.build();

	public static final Post NEW_REPOST_POST_QUOTING_REPOST_POST = Post.builder()
			.content( "CREATED POST" )
			.type( PostType.REPOST )
			.originalPost( CREATED_REPOST_POST )
			.build();

	public static final Post NEW_REPOST_POST = Post.builder()
			.content( "CREATED POST" )
			.type( PostType.REPOST )
			.originalPost( CREATED_POST )
			.build();

	public static final Post NEW_POST_WITH_ORIGINAL_POST = Post.builder()
			.content( "CREATED POST" )
			.type( PostType.REGULAR )
			.originalPost( CREATED_POST )
			.build();

}
