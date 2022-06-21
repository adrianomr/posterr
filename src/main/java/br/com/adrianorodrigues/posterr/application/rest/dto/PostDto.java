package br.com.adrianorodrigues.posterr.application.rest.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
	private UUID id;
	private String content;
}
