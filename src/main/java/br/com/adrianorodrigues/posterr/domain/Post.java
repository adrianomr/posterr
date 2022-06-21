package br.com.adrianorodrigues.posterr.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "post")
public class Post {
	private UUID id;
	private String content;
}
