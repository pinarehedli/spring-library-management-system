package com.pinarehedli.springlibrarymanagementsystem.controller;

import com.pinarehedli.springlibrarymanagementsystem.model.request.borrowing.BorrowRequestDTO;
import com.pinarehedli.springlibrarymanagementsystem.model.dto.borrowing.BorrowingDTO;
import com.pinarehedli.springlibrarymanagementsystem.model.request.borrowing.ReturnRequestDTO;
import com.pinarehedli.springlibrarymanagementsystem.service.BorrowingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/borrowings")
public class BorrowingController {

	private final BorrowingService borrowingService;

	@GetMapping("/user")
	public ResponseEntity<List<BorrowingDTO>> getUserAllBorrowings() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return ResponseEntity.ok(borrowingService.getUserAllBorrowings(username));
	}

	@GetMapping("/user/{username}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<BorrowingDTO>> getAllBorrowingsByUsername(@NotNull @PathVariable String username) {
		return ResponseEntity.ok(borrowingService.getAllBorrowingsByUsername(username));
	}

	@PostMapping("/borrow")
	public ResponseEntity<Void> borrow(@Valid @RequestBody BorrowRequestDTO dto) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		borrowingService.borrow(dto, username);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/return")
	public ResponseEntity<Void> returnBook(@Valid @RequestBody ReturnRequestDTO dto) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		borrowingService.returnBook(dto, username);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
