package br.com.adrianorodrigues.posterr.adapter.infra.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.adrianorodrigues.posterr.domain.User;
import br.com.adrianorodrigues.posterr.util.context.AbstractContextMockDataBase;
import br.com.adrianorodrigues.posterr.util.pool.domain.UsersPool;

@SpringBootTest
class UserRepositoryAdapterImplTest extends AbstractContextMockDataBase {

	@Autowired
	UserRepositoryAdapterImpl userRepositoryAdapter;

	@Test
	void findUserById() {
		User user = userRepositoryAdapter
				.findUserById( UsersPool.USER_1.getId() )
				.orElse( new User() );

		assertThat(user)
				.hasFieldOrPropertyWithValue( "username", UsersPool.USER_1.getUsername() )
				.hasFieldOrPropertyWithValue( "postsAmount", UsersPool.USER_1.getPostsAmount() )
				.hasFieldOrPropertyWithValue( "dailyPostsAmount", UsersPool.USER_1.getDailyPostsAmount() )
				.hasFieldOrPropertyWithValue( "lastPostDate", UsersPool.USER_1.getLastPostDate() );
	}

	@Test
	void saveShouldSavePost() {
		userRepositoryAdapter.save( UsersPool.USER_WITH_POST_IN_SAME_DAY );

		User user = userRepositoryAdapter
				.findUserById( UsersPool.USER_WITH_POST_IN_SAME_DAY.getId() )
				.orElse( new User() );
		assertThat(user)
				.hasFieldOrPropertyWithValue( "username", UsersPool.USER_WITH_POST_IN_SAME_DAY.getUsername() )
				.hasFieldOrPropertyWithValue( "postsAmount", UsersPool.USER_WITH_POST_IN_SAME_DAY.getPostsAmount() )
				.hasFieldOrPropertyWithValue( "dailyPostsAmount", UsersPool.USER_WITH_POST_IN_SAME_DAY.getDailyPostsAmount() )
				.hasFieldOrPropertyWithValue( "lastPostDate", UsersPool.USER_WITH_POST_IN_SAME_DAY.getLastPostDate() );
	}
}