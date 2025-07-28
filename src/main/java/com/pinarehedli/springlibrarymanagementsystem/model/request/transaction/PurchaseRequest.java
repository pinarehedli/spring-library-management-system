package com.pinarehedli.springlibrarymanagementsystem.model.request.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PurchaseRequest {

	@NotNull
	private Long bookId;

	@NotNull
	@Positive()
	private Integer quantity;

}
