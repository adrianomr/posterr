package br.com.adrianorodrigues.posterr.application.rest.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import br.com.adrianorodrigues.posterr.application.rest.config.RestContext;

@Slf4j
@Component
public class ContextInterceptor implements HandlerInterceptor {

	@Autowired
	RestContext restContext;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		restContext.initializeFromHeaders( request );
		return true;
	}

}