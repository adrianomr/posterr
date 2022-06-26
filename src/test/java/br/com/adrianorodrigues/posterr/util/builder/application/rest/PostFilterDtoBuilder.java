package br.com.adrianorodrigues.posterr.util.builder.application.rest;

import java.time.Instant;

import br.com.adrianorodrigues.posterr.application.rest.dto.PostFilterDto;

public class PostFilterDtoBuilder {
	public static PostFilterDto buildFilter() {
		return PostFilterDto.builder()
				.beginDate( Instant.now() )
				.endDate( Instant.now() )
				.myPosts( true )
				.page( 1 )
				.pageSize( 10 )
				.build();
	}
}
