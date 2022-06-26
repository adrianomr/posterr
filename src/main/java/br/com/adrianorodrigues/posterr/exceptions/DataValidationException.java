package br.com.adrianorodrigues.posterr.exceptions;

public class DataValidationException extends RuntimeException {
	public DataValidationException(String resource, String identifier) {
		super( resource + " is invalid: " + identifier );
	}
}
