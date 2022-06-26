package br.com.adrianorodrigues.posterr.domain;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostFilter {

	private Instant beginDate;
	private Instant endDate;
	private UUID userId;
	private Boolean myPosts;

	public void prepare() {
		if (isAllPosts()) {
			userId = null;
		}
	}

	private boolean isAllPosts() {
		return !Boolean.TRUE.equals( myPosts );
	}
}
