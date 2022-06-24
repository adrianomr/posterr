package br.com.adrianorodrigues.posterr.application.rest.config;

import static java.util.Objects.isNull;

import javax.servlet.http.HttpServletRequest;

import lombok.Getter;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.adrianorodrigues.posterr.exceptions.UnathorizedException;

@Component
@RequestScope
@Getter
public class RestContext {

	private String userId;

	public void initializeFromHeaders(HttpServletRequest request){
		userId = request.getHeader( "x-user-id" );
		validate();
	}

	private void validate() {
		if(isNull(userId))
			throw new UnathorizedException( "x-user-id must not be null" );
	}

}
