package br.com.adrianorodrigues.posterr.mapper.application.rest;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.adrianorodrigues.posterr.application.rest.dto.UserDto;
import br.com.adrianorodrigues.posterr.domain.User;

@Mapper
public interface UserDtoMapper {
	UserDtoMapper INSTANCE = Mappers.getMapper( UserDtoMapper.class );

	UserDto map(User post);
}
