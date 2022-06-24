package br.com.adrianorodrigues.posterr.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import br.com.adrianorodrigues.posterr.exceptions.ForbiddenException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "posterr_user")
public class User {
	public static final int POSTS_QUOTA = 5;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	@Column
	private String username;
	@Column
	private Long postsAmount;
	@Column
	private Integer dailyPostsAmount;
	@Column
	private LocalDate lastPostDate;
	@Column
	private Instant createdAt;

	public User addPost() {
		validateCanAddPost();
		updatePostsCount();
		lastPostDate = LocalDate.now();
		return this;
	}

	private void updatePostsCount() {
		postsAmount++;
		if (hasPostsDoneToday()) {
			dailyPostsAmount++;
		} else {
			dailyPostsAmount = 1;
		}
	}

	private void validateCanAddPost() {
		if(hasPostsDoneToday() && hasExceededPostsLimit())
			throw new ForbiddenException( "Too many posts created. Daily quota:" + POSTS_QUOTA );
	}

	private boolean hasExceededPostsLimit() {
		return dailyPostsAmount == POSTS_QUOTA;
	}

	private boolean hasPostsDoneToday() {
		return Objects.equals( lastPostDate, LocalDate.now() );
	}

}
