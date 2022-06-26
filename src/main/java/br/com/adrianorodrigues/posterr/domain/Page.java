package br.com.adrianorodrigues.posterr.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Page <T>{

	private List<T> elements;
	private int pageSize;
	private int currentPage;
	private int totalPages;

}
