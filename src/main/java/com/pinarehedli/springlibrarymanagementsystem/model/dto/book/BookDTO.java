package com.pinarehedli.springlibrarymanagementsystem.dto.book;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BookDTO {

	private Long id;
	private String title;
	private String author;
	private String isbn;
	private BigDecimal price;
	private Integer stock;
	private String categoryName;
}

