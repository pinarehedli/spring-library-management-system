package com.pinarehedli.springlibrarymanagementsystem.model.request.book;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateBookRequest {

	@NotBlank
	private String title;

	@NotBlank
	private String author;

	@DecimalMin("0.0")
	private BigDecimal price;

	@NotNull
	private Long categoryId;
}
