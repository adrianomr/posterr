package br.com.adrianorodrigues.posterr.infra.repository.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class PostsFilterDto {

	private Instant beginDate;
	private Instant endDate;
	private UUID userId;

}
