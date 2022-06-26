package br.com.adrianorodrigues.posterr.adapter.application.rest;

import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import br.com.adrianorodrigues.posterr.application.rest.dto.UserDto;
import br.com.adrianorodrigues.posterr.mapper.application.rest.UserDtoMapper;
import br.com.adrianorodrigues.posterr.usecase.user.FindUser;

@Service
@RequiredArgsConstructor
public class UserControllerAdapter {

	private final FindUser findUser;

	public UserDto findCurrent(String userId){
		var user = findUser.execute( UUID.fromString( userId ) );
		return UserDtoMapper.INSTANCE.map(user);
	}

}
