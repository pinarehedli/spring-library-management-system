package com.pinarehedli.springlibrarymanagementsystem.service;

import com.pinarehedli.springlibrarymanagementsystem.exception.ResourceAlreadyExistsException;
import com.pinarehedli.springlibrarymanagementsystem.exception.ResourceNotFoundException;
import com.pinarehedli.springlibrarymanagementsystem.model.dto.book.BookDTO;
import com.pinarehedli.springlibrarymanagementsystem.model.request.book.CreateBookRequest;
import com.pinarehedli.springlibrarymanagementsystem.model.entity.Book;
import com.pinarehedli.springlibrarymanagementsystem.model.entity.Category;
import com.pinarehedli.springlibrarymanagementsystem.mapper.BookMapper;
import com.pinarehedli.springlibrarymanagementsystem.model.request.book.StockRequest;
import com.pinarehedli.springlibrarymanagementsystem.model.request.book.UpdateBookRequest;
import com.pinarehedli.springlibrarymanagementsystem.repository.BookRepository;
import com.pinarehedli.springlibrarymanagementsystem.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
				.orElseThrow(() -> new ResourceNotFoundException("Book not found"));

		return BookMapper.toDTO(book);

	}


	public List<BookDTO> findBookByCategoryName(String categoryName) {
		Category category = categoryRepository
				.findCategoryByName(categoryName)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));

		List<Book> books = bookRepository.findAllByCategoryId(category.getId());

		return books.stream()
				.map(BookMapper::toDTO)
				.toList();
	}

	public BookDTO createBook(CreateBookRequest request) {
		Category category = categoryRepository
				.findById(request.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));

		Optional<Book> book = bookRepository.findBookByIsbn(request.getIsbn());

		if (book.isPresent()) {
			throw new ResourceAlreadyExistsException("Book already exists");
		}

		Book savedBook = bookRepository.save(BookMapper.fromRequest(request, category));

		return BookMapper.toDTO(savedBook);
	}

	public BookDTO updateBook(UpdateBookRequest request, Long id) {

		Book book = bookRepository
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found"));

		book.setTitle(request.getTitle());
		book.setAuthor(request.getAuthor());
		book.setPrice(request.getPrice());

		Category category = categoryRepository
				.findById(request.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));


		book.setCategory(category);

		Book savedBook = bookRepository.save(book);

		return BookMapper.toDTO(savedBook);
	}

	public void deleteBook(Long id) {
		Book book = bookRepository
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found"));

		bookRepository.delete(book);
	}

	public BookDTO addStock(StockRequest stockRequest, Long id) {
		Book book = bookRepository
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found"));

		book.setStock(book.getStock() + stockRequest.getStock());
		bookRepository.save(book);

		return BookMapper.toDTO(book);
	}
}
