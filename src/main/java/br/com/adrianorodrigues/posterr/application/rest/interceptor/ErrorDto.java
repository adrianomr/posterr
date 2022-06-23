package br.com.adrianorodrigues.posterr.application.rest.interceptor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.springframework.http.HttpStatus;

import br.com.adrianorodrigues.posterr.exceptions.DataValidationException;
import br.com.adrianorodrigues.posterr.exceptions.ResourceNotFoundException;

@AllArgsConstructor
@Builder
@Data
public class ErrorDto {

    private int status;
    private String message;

    public ErrorDto(ResourceNotFoundException ex, HttpStatus status) {
        this.status = status.value();
        message = ex.getMessage();
    }

	public ErrorDto(DataValidationException ex, HttpStatus status) {
		this.status = status.value();
		message = ex.getMessage();
	}
}
