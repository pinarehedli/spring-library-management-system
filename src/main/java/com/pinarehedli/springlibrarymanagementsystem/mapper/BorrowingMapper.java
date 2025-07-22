package com.pinarehedli.springlibrarymanagementsystem.mapper;

import com.pinarehedli.springlibrarymanagementsystem.dto.borrowing.BorrowingDTO;
import com.pinarehedli.springlibrarymanagementsystem.entity.Borrowing;

public class BorrowingMapper {

	public static BorrowingDTO toDTO(Borrowing borrowing) {
		return BorrowingDTO.builder()
				.id(borrowing.getId())
				.bookTitle(borrowing.getBook().getTitle())
				.username(borrowing.getUser().getUsername())
				.borrowDate(borrowing.getBorrowDate())
				.returnDate(borrowing.getReturnDate())
				.returned(borrowing.getReturned())
				.build();
	}



}
