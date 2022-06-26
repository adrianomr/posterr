package br.com.adrianorodrigues.posterr.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PaginationFilter<T> {

	private int page;
	private int pageSize;
	private T filter;

}
