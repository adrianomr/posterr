package br.com.adrianorodrigues.posterr.application.rest.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PageDto <T> {
	private int page;
	private int pageSize;
	private int totalElements;
	private int totalPages;
	private List<T> data;
}
