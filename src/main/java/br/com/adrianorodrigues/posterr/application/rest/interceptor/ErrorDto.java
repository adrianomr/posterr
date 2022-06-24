package br.com.adrianorodrigues.posterr.application.rest.interceptor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Builder
@Data
public class ErrorDto {

	private int status;
	private String message;

	public ErrorDto(RuntimeException ex, HttpStatus status) {
		this.status = status.value();
		message = ex.getMessage();
	}

}
