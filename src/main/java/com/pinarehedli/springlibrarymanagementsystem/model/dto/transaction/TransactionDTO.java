package com.pinarehedli.springlibrarymanagementsystem.dto.transaction;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class TransactionDTO {

	private Long id;

	private String bookTitle;

	private String username;

	private Integer quantity;

	private BigDecimal totalPrice;

	private LocalDate transactionDate;
}

