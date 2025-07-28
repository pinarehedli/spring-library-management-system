package com.pinarehedli.springlibrarymanagementsystem.mapper;

import com.pinarehedli.springlibrarymanagementsystem.model.dto.transaction.TransactionDTO;
import com.pinarehedli.springlibrarymanagementsystem.model.entity.Transaction;

public class TransactionMapper {

	public static TransactionDTO toDTO(Transaction transaction) {
		return TransactionDTO.builder()
				.id(transaction.getId())
				.bookTitle(transaction.getBook().getTitle())
				.username(transaction.getUser().getUsername())
				.quantity(transaction.getQuantity())
				.totalPrice(transaction.getTotalPrice())
				.transactionDate(transaction.getTransactionDate())
				.build();

	}
}
