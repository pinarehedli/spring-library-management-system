package com.pinarehedli.springlibrarymanagementsystem.mapper;

import com.pinarehedli.springlibrarymanagementsystem.model.dto.book.BookDTO;
import com.pinarehedli.springlibrarymanagementsystem.model.request.book.CreateBookRequest;
import com.pinarehedli.springlibrarymanagementsystem.model.entity.Book;
import com.pinarehedli.springlibrarymanagementsystem.model.entity.Category;

public class BookMapper {

	public static BookDTO toDTO(Book book) {
		return BookDTO.builder()
				.id(book.getId())
				.title(book.getTitle())
				.author(book.getAuthor())
				.isbn(book.getIsbn())
				.price(book.getPrice())
				.stock(book.getStock())
				.categoryName(book.getCategory().getName())
				.build();

	}

	public static Book fromRequest(CreateBookRequest request, Category category) {
		return Book.builder()
				.title(request.getTitle())
				.author(request.getAuthor())
				.isbn(request.getIsbn())
				.price(request.getPrice())
				.stock(request.getStock())
				.category(category)
				.build();
	}
}
