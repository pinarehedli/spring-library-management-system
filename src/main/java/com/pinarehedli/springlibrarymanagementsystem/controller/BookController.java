package com.pinarehedli.springlibrarymanagementsystem.controller;

import com.pinarehedli.springlibrarymanagementsystem.model.dto.book.BookDTO;
import com.pinarehedli.springlibrarymanagementsystem.model.request.book.CreateBookRequest;
import com.pinarehedli.springlibrarymanagementsystem.model.request.book.StockRequest;
import com.pinarehedli.springlibrarymanagementsystem.model.request.book.UpdateBookRequest;
import com.pinarehedli.springlibrarymanagementsystem.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/books")
public class BookController {

	private final BookService bookService;

	@GetMapping()
	public ResponseEntity<List<BookDTO>> findAllBooks() {
		return ResponseEntity.ok(bookService.findAllBooks());
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookDTO> findBookById(@Min(1) @PathVariable Long id) {
		return ResponseEntity.ok(bookService.findBookById(id));
	}

	@GetMapping("/category")
	public ResponseEntity<List<BookDTO>> findBookByCategoryName(@NotBlank @RequestParam(name = "category_name") String categoryName) {
		return ResponseEntity.ok(bookService.findBookByCategoryName(categoryName));
	}

	@PostMapping()
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BookDTO> createBook(@Valid @RequestBody CreateBookRequest bookRequest) {
		return new ResponseEntity<>(bookService.createBook(bookRequest), HttpStatus.CREATED);
	}

	@PutMapping("/stock/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BookDTO> addStock(@Valid @RequestBody StockRequest stockRequest,
	                                        @Min(1) @PathVariable Long id) {
		return ResponseEntity.ok(bookService.addStock(stockRequest, id));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BookDTO> updateBook(@Valid @RequestBody UpdateBookRequest request,
	                                          @Min(1) @PathVariable Long id) {

		System.out.println("Request reached");
		return ResponseEntity.ok(bookService.updateBook(request, id));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteBook(@Min(1) @PathVariable Long id) {
		bookService.deleteBook(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

