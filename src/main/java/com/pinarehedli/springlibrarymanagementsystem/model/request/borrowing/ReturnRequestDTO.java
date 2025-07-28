package com.pinarehedli.springlibrarymanagementsystem.model.request.borrowing;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ReturnRequestDTO {

	@Positive
	private Long borrowingId;

}
