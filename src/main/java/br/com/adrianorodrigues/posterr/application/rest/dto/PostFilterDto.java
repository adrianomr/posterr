package br.com.adrianorodrigues.posterr.application.rest.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class PostFilterDto {

	private Instant beginDate;
	private Instant endDate;
	private Boolean myPosts;
	private Integer page;
	private Integer pageSize;

}
