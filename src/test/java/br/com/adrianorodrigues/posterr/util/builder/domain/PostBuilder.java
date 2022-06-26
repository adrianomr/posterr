package br.com.adrianorodrigues.posterr.util.builder.domain;

import java.time.Instant;
import java.util.UUID;

import br.com.adrianorodrigues.posterr.domain.Post;
import br.com.adrianorodrigues.posterr.enums.PostType;

public class PostBuilder {

	public static Post buildNewPost() {
		return Post.builder()
				.content( "CREATED POST" )
				.type( PostType.REGULAR )
				.userId( UserBuilder.buildUser1().getId() )
				.createdAt( Instant.now() )
				.build();
	}

	public static Post buildNewQuotaPostWithoutOriginal() {
		return Post.builder()
				.content( "CREATED POST" )
				.type( PostType.QUOTE )
				.build();
	}

	public static Post buildNewRepostPostWithoutOriginal() {
		return Post.builder()
				.content( "CREATED POST" )
				.type( PostType.REPOST )
				.build();
	}

	public static Post buildCreatedPost() {
		return Post.builder()
				.id( UUID.randomUUID() )
				.content( "CREATED POST" )
				.type( PostType.REGULAR )
				.userId( UserBuilder.buildUser1().getId() )
				.build();
	}

	public static Post buildCreatedQuotePost() {
		return Post.builder()
				.id( UUID.randomUUID() )
				.content( "CREATED POST" )
				.type( PostType.QUOTE )
				.originalPost( buildCreatedPost() )
				.build();
	}

	public static Post buildCreatedRepostPost() {
		return Post.builder()
				.id( UUID.randomUUID() )
				.content( "CREATED POST" )
				.type( PostType.REPOST )
				.originalPost( buildCreatedPost() )
				.build();
	}

	public static Post buildNewQuotePost() {
		return Post.builder()
				.content( "CREATED POST" )
				.type( PostType.QUOTE )
				.originalPost( buildCreatedPost() )
				.build();
	}

	public static Post buildNewQuotePostQuotingQuotePost() {
		return Post.builder()
				.content( "CREATED POST" )
				.type( PostType.QUOTE )
				.originalPost( buildCreatedQuotePost() )
				.build();
	}

	public static Post buildNewRepostPostReferencingRepostPost() {
		return Post.builder()
				.content( "CREATED POST" )
				.type( PostType.REPOST )
				.originalPost( buildCreatedRepostPost() )
				.build();
	}

	public static Post buildNewRepostPost() {
		return Post.builder()
				.content( "CREATED POST" )
				.type( PostType.REPOST )
				.originalPost( buildCreatedPost() )
				.build();
	}

	public static final Post buildNewPostWithOriginalPost() {
		return Post.builder()
				.content( "CREATED POST" )
				.type( PostType.REGULAR )
				.originalPost( buildCreatedPost() )
				.build();
	}

}
