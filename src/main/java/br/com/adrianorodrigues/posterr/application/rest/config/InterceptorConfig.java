package br.com.adrianorodrigues.posterr.application.rest.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.adrianorodrigues.posterr.application.rest.interceptor.ContextInterceptor;

@Component
public record InterceptorConfig(
        ContextInterceptor interceptor)
        implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor( interceptor );
    }

}