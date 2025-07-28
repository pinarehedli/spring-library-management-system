package com.pinarehedli.springlibrarymanagementsystem.exception;

public class BookOutOfStockException extends RuntimeException {
	public BookOutOfStockException(String message) {
		super(message);
	}
}
