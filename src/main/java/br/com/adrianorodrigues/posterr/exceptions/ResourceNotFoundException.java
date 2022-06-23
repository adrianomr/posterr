package br.com.adrianorodrigues.posterr.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(String resource, String identifier) {
		super( "Resource " + resource + " not found for identifier:" + identifier );
	}
}
