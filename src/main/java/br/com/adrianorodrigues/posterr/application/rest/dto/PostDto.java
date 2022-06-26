package br.com.adrianorodrigues.posterr.application.rest.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import br.com.adrianorodrigues.posterr.enums.PostType;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDto {
	private UUID id;
	private String content;
	private String originalPostContent;
	private UUID originalPostUserId;
	private PostType type;
	private UUID originalPostId;
}
