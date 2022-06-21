package br.com.adrianorodrigues.posterr.mapper.domain;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.adrianorodrigues.posterr.application.rest.dto.PostDto;
import br.com.adrianorodrigues.posterr.domain.Post;

@Mapper
public interface PostMapper {
	PostMapper INSTANCE = Mappers.getMapper( PostMapper.class );

	Post map(PostDto post);
}
