package com.pinarehedli.springlibrarymanagementsystem.dto.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TransactionRequest {

	@NotNull
	private Long bookId;

	@NotNull
	@Positive()
	private Integer quantity;

}
