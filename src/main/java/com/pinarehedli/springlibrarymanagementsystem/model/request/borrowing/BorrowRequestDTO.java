package com.pinarehedli.springlibrarymanagementsystem.model.dto.borrowing;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BorrowRequestDTO {

	@NotNull
	private Long bookId;
}
