package br.com.adrianorodrigues.posterr.usecase.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.adrianorodrigues.posterr.adapter.infra.repository.UserRepositoryAdapter;
import br.com.adrianorodrigues.posterr.exceptions.ForbiddenException;
import br.com.adrianorodrigues.posterr.util.pool.domain.PostsPool;
import br.com.adrianorodrigues.posterr.util.pool.domain.UsersPool;

@ExtendWith(MockitoExtension.class)
class UpdateUserImplTest {

	@Mock
	UserRepositoryAdapter userRepositoryAdapter;
	@InjectMocks
	UpdateUserImpl updateUser;

	@Test
	void executeWhenSuccessShouldUpdatePostsCount() {
		var post = PostsPool.NEW_POST;
		var user = UsersPool.USER_1;
		when( userRepositoryAdapter.findUserById( user.getId() ) )
				.thenReturn( Optional.of( user ) );

		updateUser.execute( post );

		assertThat(user)
				.hasFieldOrPropertyWithValue( "postsAmount", 1L )
				.hasFieldOrPropertyWithValue( "dailyPostsAmount", 1 );
	}

	@Test
	void executeWhenSuccessShouldUpdateLastPostDate() {
		var post = PostsPool.NEW_POST;
		var user = UsersPool.USER_1;
		when( userRepositoryAdapter.findUserById( user.getId() ) )
				.thenReturn( Optional.of( user ) );

		updateUser.execute( post );

		assertThat(user)
				.hasFieldOrPropertyWithValue( "lastPostDate", LocalDate.now() );
	}

	@Test
	void executeWhenSuccessShouldSaveUser() {
		var post = PostsPool.NEW_POST;
		var user = UsersPool.USER_1;
		when( userRepositoryAdapter.findUserById( user.getId() ) )
				.thenReturn( Optional.of( user ) );

		updateUser.execute( post );

		verify( userRepositoryAdapter ).save( user );
	}

	@Test
	void executeWhenUserHasPostInSameDayShouldUpdatePostsCount() {
		var post = PostsPool.NEW_POST;
		var user = UsersPool.USER_WITH_POST_IN_SAME_DAY;
		when( userRepositoryAdapter.findUserById( user.getId() ) )
				.thenReturn( Optional.of( user ) );

		updateUser.execute( post );

		assertThat(user)
				.hasFieldOrPropertyWithValue( "postsAmount", 2L )
				.hasFieldOrPropertyWithValue( "dailyPostsAmount", 2 );
	}

	@Test
	void executeWhenUserHasPostInOtherDayShouldResetDailyPostsCount() {
		var post = PostsPool.NEW_POST;
		var user = UsersPool.USER_WITH_POST_IN_OTHER_DAY;
		when( userRepositoryAdapter.findUserById( user.getId() ) )
				.thenReturn( Optional.of( user ) );

		updateUser.execute( post );

		assertThat(user)
				.hasFieldOrPropertyWithValue( "dailyPostsAmount", 1 );
	}

	@Test
	void executeWhenUserHasPostInOtherDayShouldUpdatePostsCount() {
		var post = PostsPool.NEW_POST;
		var user = UsersPool.USER_WITH_POST_IN_OTHER_DAY;
		when( userRepositoryAdapter.findUserById( user.getId() ) )
				.thenReturn( Optional.of( user ) );

		updateUser.execute( post );

		assertThat(user)
				.hasFieldOrPropertyWithValue( "postsAmount", 11L );
	}

	@Test
	void executeWhenUserHasMoreFivePostTodayShouldThrowLimitExceededException() {
		var post = PostsPool.NEW_POST;
		var user = UsersPool.USER_WITH_QUOTA_REACHED;
		when( userRepositoryAdapter.findUserById( user.getId() ) )
				.thenReturn( Optional.of( user ) );

		assertThrows( ForbiddenException.class, () -> updateUser.execute( post ));
	}
}