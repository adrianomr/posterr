package br.com.adrianorodrigues.posterr.adapter.infra.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.adrianorodrigues.posterr.domain.User;
import br.com.adrianorodrigues.posterr.util.context.AbstractContextMockDataBase;
import br.com.adrianorodrigues.posterr.util.builder.domain.UserBuilder;

@SpringBootTest
class UserRepositoryAdapterImplTest extends AbstractContextMockDataBase {

	@Autowired
	UserRepositoryAdapterImpl userRepositoryAdapter;

	@Test
	void findUserById() {
		User user1 = UserBuilder.buildUser1();

		User user = userRepositoryAdapter
				.findUserById( user1.getId() )
				.orElse( new User() );

		assertThat(user)
				.hasFieldOrPropertyWithValue( "username", user1.getUsername() )
				.hasFieldOrPropertyWithValue( "postsAmount", user1.getPostsAmount() )
				.hasFieldOrPropertyWithValue( "dailyPostsAmount", user1.getDailyPostsAmount() )
				.hasFieldOrPropertyWithValue( "lastPostDate", user1.getLastPostDate() );
	}

	@Test
	void saveShouldSavePost() {
		User userWithPostInSameDay = UserBuilder.buildUserWithPostInSameDay();
		userRepositoryAdapter.save( userWithPostInSameDay );

		User user = userRepositoryAdapter
				.findUserById( userWithPostInSameDay.getId() )
				.orElse( new User() );
		assertThat(user)
				.hasFieldOrPropertyWithValue( "username", userWithPostInSameDay.getUsername() )
				.hasFieldOrPropertyWithValue( "postsAmount", userWithPostInSameDay.getPostsAmount() )
				.hasFieldOrPropertyWithValue( "dailyPostsAmount", userWithPostInSameDay.getDailyPostsAmount() )
				.hasFieldOrPropertyWithValue( "lastPostDate", userWithPostInSameDay.getLastPostDate() );
	}
}