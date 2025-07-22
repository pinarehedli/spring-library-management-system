package com.pinarehedli.springlibrarymanagementsystem.dto.borrowing;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BorrowingDTO {

	private Long id;

	private String bookTitle;

	private String username;

	private LocalDate borrowDate;

	private LocalDate returnDate;

	private Boolean returned;
}

