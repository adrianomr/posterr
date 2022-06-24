package br.com.adrianorodrigues.posterr.exceptions;

public class UnathorizedException extends RuntimeException {
	public UnathorizedException(String message) {
		super( message );
	}
}
