package com.pinarehedli.springlibrarymanagementsystem.model.dto.borrowing;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ReturnRequestDTO {

	@Positive
	private Long borrowingId;

	@NotNull
	private String cardNumber;
}
