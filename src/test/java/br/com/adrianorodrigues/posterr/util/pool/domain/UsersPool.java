package br.com.adrianorodrigues.posterr.util.pool.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import br.com.adrianorodrigues.posterr.domain.User;

public class UsersPool {
	public static final User USER_1 = User.builder()
			.id( UUID.fromString( "d201e18b-83b0-4ea1-928d-81dee88eb8eb" ) )
			.dailyPostsAmount( 0 )
			.postsAmount( 0L )
			.username( "user1" )
			.createdAt( Instant.now() )
			.build();

	public static final User USER_WITH_POST_IN_SAME_DAY = User.builder()
			.id( UUID.fromString( "d201e18b-83b0-4ea1-928d-81dee88eb8eb" ) )
			.dailyPostsAmount( 1 )
			.postsAmount( 1L )
			.username( "user1" )
			.lastPostDate( LocalDate.now() )
			.createdAt( Instant.now() )
			.build();

	public static final User USER_WITH_POST_IN_OTHER_DAY = User.builder()
			.id( UUID.fromString( "d201e18b-83b0-4ea1-928d-81dee88eb8eb" ) )
			.dailyPostsAmount( 4 )
			.postsAmount( 10L )
			.username( "user1" )
			.lastPostDate( LocalDate.of(2022, 1, 1) )
			.createdAt( Instant.now() )
			.build();

	public static final User USER_WITH_QUOTA_REACHED = User.builder()
			.id( UUID.fromString( "d201e18b-83b0-4ea1-928d-81dee88eb8eb" ) )
			.dailyPostsAmount( 5 )
			.postsAmount( 10L )
			.username( "user1" )
			.lastPostDate( LocalDate.now() )
			.createdAt( Instant.now() )
			.build();
}
