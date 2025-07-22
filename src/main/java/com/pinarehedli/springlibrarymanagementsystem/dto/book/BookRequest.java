package com.pinarehedli.springlibrarymanagementsystem.dto.book;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;

@Data
public class BookRequest {

	@NotBlank()
	private String title;

	@NotBlank()
	private String author;

	@ISBN
	private String isbn;

	@DecimalMin("0.0")
	private BigDecimal price;

	@Min(0)
	private Integer stock;

	@NotNull()
	private Long categoryId;
}

