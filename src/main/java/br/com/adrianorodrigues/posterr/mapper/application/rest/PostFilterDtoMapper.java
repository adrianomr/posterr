package br.com.adrianorodrigues.posterr.mapper.application.rest;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.adrianorodrigues.posterr.application.rest.dto.PostFilterDto;
import br.com.adrianorodrigues.posterr.domain.PostFilter;

@Mapper
public interface PostFilterDtoMapper {
	PostFilterDtoMapper INSTANCE = Mappers.getMapper( PostFilterDtoMapper.class );

	PostFilter map(PostFilterDto post);
}
