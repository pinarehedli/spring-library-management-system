package com.pinarehedli.springlibrarymanagementsystem.service;

import com.pinarehedli.springlibrarymanagementsystem.dto.transaction.TransactionDTO;
import com.pinarehedli.springlibrarymanagementsystem.dto.transaction.TransactionRequest;
import com.pinarehedli.springlibrarymanagementsystem.entity.Book;
import com.pinarehedli.springlibrarymanagementsystem.entity.Transaction;
import com.pinarehedli.springlibrarymanagementsystem.entity.User;
import com.pinarehedli.springlibrarymanagementsystem.exception.BookNotFoundException;
import com.pinarehedli.springlibrarymanagementsystem.exception.BookOutOfStockException;
import com.pinarehedli.springlibrarymanagementsystem.exception.InsufficientBalanceException;
import com.pinarehedli.springlibrarymanagementsystem.exception.UserNotFoundException;
import com.pinarehedli.springlibrarymanagementsystem.mapper.TransactionMapper;
import com.pinarehedli.springlibrarymanagementsystem.repository.BookRepository;
import com.pinarehedli.springlibrarymanagementsystem.repository.TransactionRepository;
import com.pinarehedli.springlibrarymanagementsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

	private final TransactionRepository transactionRepository;
	private final UserRepository userRepository;
	private final BookRepository bookRepository;

	@Transactional
	public void purchase(TransactionRequest request, String username) {
		User user = userRepository
				.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		Book book = bookRepository
				.findById(request.getBookId())
				.orElseThrow(() -> new BookNotFoundException("Book not found"));

		Integer quantity = request.getQuantity();
		BigDecimal price = book.getPrice();

		BigDecimal total = price.multiply(BigDecimal.valueOf(quantity));

		if (user.getBalance().compareTo(total) >= 0) {
			if (book.getStock() > quantity) {
				book.setStock(book.getStock() - quantity);
				user.setBalance(user.getBalance().subtract(total));
			} else {
				throw new BookOutOfStockException("Book '" + book.getTitle() + "' is out of stock");
			}
		} else {
			throw new InsufficientBalanceException("Balance is not enough to pay");
		}

		Transaction transaction = Transaction.builder()
				.user(user)
				.book(book)
				.totalPrice(total)
				.quantity(quantity)
				.transactionDate(LocalDate.now())
				.build();

		userRepository.save(user);
		bookRepository.save(book);
		transactionRepository.save(transaction);
	}

	public List<TransactionDTO> getAllTransactions(String username) {

		User user = userRepository
				.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		List<Transaction> transactions = transactionRepository.getTransactionsByUserId(user.getId());

		return transactions.stream()
				.map(TransactionMapper::toDTO)
				.toList();
	}


}
