package br.com.adrianorodrigues.posterr.application.rest.config;

import static java.util.Objects.isNull;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.com.adrianorodrigues.posterr.exceptions.UnathorizedException;

@Component
@RequestScope
@Slf4j
public class RestContext {

	private String userId;

	public void initializeFromHeaders(HttpServletRequest request) {
		userId = request.getHeader( "x-user-id" );
	}

	public String getUserId() {
		if (isNull( userId )) {
			throw new UnathorizedException( "x-user-id must not be null" );
		}
		return userId;
	}
}
