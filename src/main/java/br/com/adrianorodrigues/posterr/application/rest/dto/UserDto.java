package br.com.adrianorodrigues.posterr.application.rest.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDto {

	private UUID id;
	private String username;
	private Long postsAmount;
	private Integer dailyPostsAmount;
	private LocalDate lastPostDate;
	private Instant createdAt;

}
