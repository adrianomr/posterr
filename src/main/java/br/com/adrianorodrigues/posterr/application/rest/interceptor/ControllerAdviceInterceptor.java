package br.com.adrianorodrigues.posterr.application.rest.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.adrianorodrigues.posterr.exceptions.DataValidationException;
import br.com.adrianorodrigues.posterr.exceptions.ResourceNotFoundException;
import br.com.adrianorodrigues.posterr.exceptions.UnathorizedException;

@ControllerAdvice
public class ControllerAdviceInterceptor
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<ErrorDto> handleExceptions(
            ResourceNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorDto errorDto = new ErrorDto( ex, status );
        return new ResponseEntity<>(errorDto, status );
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorDto> handleExceptions(
            DataValidationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorDto errorDto = new ErrorDto( ex, status );
        return new ResponseEntity<>(errorDto, status );
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorDto> handleExceptions(
            UnathorizedException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorDto errorDto = new ErrorDto( ex, status );
        return new ResponseEntity<>(errorDto, status );
    }
}