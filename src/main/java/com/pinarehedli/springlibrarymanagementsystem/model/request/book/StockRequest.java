package com.pinarehedli.springlibrarymanagementsystem.model.request.book;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class StockRequest {

	@NotNull
	@Positive
	private Integer stock;

}
