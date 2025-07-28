package com.pinarehedli.springlibrarymanagementsystem.controller;

import com.pinarehedli.springlibrarymanagementsystem.model.dto.transaction.TransactionDTO;
import com.pinarehedli.springlibrarymanagementsystem.model.request.transaction.PurchaseRequest;
import com.pinarehedli.springlibrarymanagementsystem.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/transactions")
public class TransactionController {

	private final TransactionService transactionService;

	@GetMapping("/user")
	public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return ResponseEntity.ok(transactionService.getAllTransactions(username));
	}

	@PostMapping("/purchase")
	public ResponseEntity<String> purchase(@Valid @RequestBody PurchaseRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		transactionService.purchase(request, username);
		return ResponseEntity.ok("Operation finished successfully");
	}
}
