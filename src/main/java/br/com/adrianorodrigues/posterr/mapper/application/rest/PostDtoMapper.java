package br.com.adrianorodrigues.posterr.mapper.application.rest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.adrianorodrigues.posterr.application.rest.dto.PostDto;
import br.com.adrianorodrigues.posterr.domain.Post;

@Mapper
public interface PostDtoMapper {
	PostDtoMapper INSTANCE = Mappers.getMapper( PostDtoMapper.class );

	@Mapping( source = "originalPost.id", target = "originalPostId")
	PostDto map(Post post);

	PostDto map(PostDto post);
}
