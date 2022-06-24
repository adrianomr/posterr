package br.com.adrianorodrigues.posterr.util.pool.domain;

import java.util.UUID;

import br.com.adrianorodrigues.posterr.domain.User;

public class UserPool {
	public static final User USER_1 = User.builder()
			.id( UUID.fromString( "d201e18b-83b0-4ea1-928d-81dee88eb8eb" ) )
			.build();
}
