package br.com.adrianorodrigues.posterr.exceptions;

public class ForbiddenException extends RuntimeException{
	public ForbiddenException(String message) {
		super( message );
	}
}
