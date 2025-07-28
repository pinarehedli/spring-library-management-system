package com.pinarehedli.springlibrarymanagementsystem.service;

import com.pinarehedli.springlibrarymanagementsystem.exception.BookOutOfStockException;
import com.pinarehedli.springlibrarymanagementsystem.exception.InsufficientBalanceException;
import com.pinarehedli.springlibrarymanagementsystem.exception.ResourceNotFoundException;
import com.pinarehedli.springlibrarymanagementsystem.model.dto.transaction.TransactionDTO;
import com.pinarehedli.springlibrarymanagementsystem.model.request.transaction.PurchaseRequest;
import com.pinarehedli.springlibrarymanagementsystem.model.entity.Book;
import com.pinarehedli.springlibrarymanagementsystem.model.entity.Transaction;
import com.pinarehedli.springlibrarymanagementsystem.model.entity.User;
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
	public void purchase(PurchaseRequest request, String username) {
		User user = userRepository
				.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Book book = bookRepository
				.findById(request.getBookId())
				.orElseThrow(() -> new ResourceNotFoundException("Book not found"));

		Integer quantity = request.getQuantity();
		BigDecimal price = book.getPrice();

		BigDecimal total = price.multiply(BigDecimal.valueOf(quantity));


		if (book.getStock() > quantity) {
			if (user.getBalance().compareTo(total) > 0) {
				user.setBalance(user.getBalance().subtract(total));
				book.setStock(book.getStock() - quantity);

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
			} else {
				throw new InsufficientBalanceException("Balance isn't enough");
			}

		} else {
			throw new BookOutOfStockException("Book '" + book.getTitle() + "' is out of stock");
		}
	}

	public List<TransactionDTO> getAllTransactions(String username) {

		User user = userRepository
				.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		List<Transaction> transactions = transactionRepository.getTransactionsByUserId(user.getId());

		return transactions.stream()
				.map(TransactionMapper::toDTO)
				.toList();
	}


}
