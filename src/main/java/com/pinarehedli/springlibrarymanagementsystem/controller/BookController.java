package com.pinarehedli.springlibrarymanagementsystem.controller;

import com.pinarehedli.springlibrarymanagementsystem.dto.book.BookDTO;
import com.pinarehedli.springlibrarymanagementsystem.dto.book.BookRequest;
import com.pinarehedli.springlibrarymanagementsystem.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/books")
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

	@PostMapping()
	public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookRequest bookRequest) {
		return new ResponseEntity<>(bookService.addBook(bookRequest), HttpStatus.CREATED);
	}

	// ADMIN
	@PutMapping("/{id}")
	public ResponseEntity<BookDTO> updateBook(@RequestBody BookRequest request, @Min(1) @PathVariable Long id) {
		return new ResponseEntity<>(bookService.updateBook(request, id), HttpStatus.NO_CONTENT);
	}

	// ADMIN
	@DeleteMapping("/{id}")
	public String deleteBook(@Min(1) @PathVariable Long id) {
		bookService.deleteBook(id);
		return "Book deleted successfully!";
	}
}


//// Kitab silmək (Admin)
//DELETE /api/books/{id}
