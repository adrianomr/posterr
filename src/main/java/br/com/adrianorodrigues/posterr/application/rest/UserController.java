package br.com.adrianorodrigues.posterr.application.rest;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.adrianorodrigues.posterr.adapter.application.rest.UserControllerAdapter;
import br.com.adrianorodrigues.posterr.application.rest.config.RestContext;
import br.com.adrianorodrigues.posterr.application.rest.dto.UserDto;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

	private final UserControllerAdapter adapter;
	private final RestContext restContext;

	@GetMapping("{userId}")
	public UserDto getUserDetails(@PathVariable String userId){
		if(userId.equalsIgnoreCase( "current" ))
			userId = restContext.getUserId();
		return adapter.findCurrent( userId );
	}

}
