package com.pinarehedli.springlibrarymanagementsystem.service;

import com.pinarehedli.springlibrarymanagementsystem.dto.book.BookDTO;
import com.pinarehedli.springlibrarymanagementsystem.dto.book.BookRequest;
import com.pinarehedli.springlibrarymanagementsystem.entity.Book;
import com.pinarehedli.springlibrarymanagementsystem.entity.Category;
import com.pinarehedli.springlibrarymanagementsystem.exception.BookNotFoundException;
import com.pinarehedli.springlibrarymanagementsystem.exception.CategoryNotFoundException;
import com.pinarehedli.springlibrarymanagementsystem.mapper.BookMapper;
import com.pinarehedli.springlibrarymanagementsystem.repository.BookRepository;
import com.pinarehedli.springlibrarymanagementsystem.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	private final CategoryRepository categoryRepository;

	public List<BookDTO> findAllBooks() {
		List<Book> books = bookRepository.findAll();

		return books.stream()
				.map(BookMapper::toDTO)
				.toList();
	}

	public BookDTO findBookById(Long id) {
		Book book = bookRepository
				.findById(id)
				.orElseThrow(() -> new BookNotFoundException("Book not found"));

		return BookMapper.toDTO(book);

	}

	public List<BookDTO> findBookByCategoryId(Long categoryId) {
		List<Book> books = bookRepository.findAllByCategoryId(categoryId);

		return books.stream()
				.map(BookMapper::toDTO)
				.toList();
	}

	public BookDTO addBook(BookRequest request) {
		Category category = categoryRepository
				.findById(request.getCategoryId())
				.orElseThrow(() -> new CategoryNotFoundException("Category not found"));

		Book savedBook = bookRepository.save(BookMapper.fromRequest(request, category));

		return BookMapper.toDTO(savedBook);
	}

	public BookDTO updateBook(BookRequest request, Long id) {

		Book book = bookRepository
				.findById(id)
				.orElseThrow(() -> new BookNotFoundException("Book not found"));

		Category category = categoryRepository
				.findById(request.getCategoryId())
				.orElseThrow(() -> new CategoryNotFoundException("Category not found"));

		book.setTitle(request.getTitle());
		book.setAuthor(request.getAuthor());
		book.setIsbn(request.getIsbn());
		book.setPrice(request.getPrice());
		book.setStock(request.getStock());
		book.setCategory(category);

		Book savedBook = bookRepository.save(book);

		return BookMapper.toDTO(savedBook);
	}

	public void deleteBook(Long id) {
		Book book = bookRepository
				.findById(id)
				.orElseThrow(() -> new BookNotFoundException("Book not found"));

		bookRepository.delete(book);
	}
}
