package com.pinarehedli.springlibrarymanagementsystem.service;

import com.pinarehedli.springlibrarymanagementsystem.exception.ResourceAlreadyExistsException;
import com.pinarehedli.springlibrarymanagementsystem.exception.ResourceNotFoundException;
import com.pinarehedli.springlibrarymanagementsystem.model.request.borrowing.BorrowRequestDTO;
import com.pinarehedli.springlibrarymanagementsystem.model.dto.borrowing.BorrowingDTO;
import com.pinarehedli.springlibrarymanagementsystem.model.request.borrowing.ReturnRequestDTO;
import com.pinarehedli.springlibrarymanagementsystem.model.entity.Book;
import com.pinarehedli.springlibrarymanagementsystem.model.entity.Borrowing;
import com.pinarehedli.springlibrarymanagementsystem.model.entity.User;
import com.pinarehedli.springlibrarymanagementsystem.exception.BookOutOfStockException;
import com.pinarehedli.springlibrarymanagementsystem.exception.InsufficientBalanceException;
import com.pinarehedli.springlibrarymanagementsystem.mapper.BorrowingMapper;
import com.pinarehedli.springlibrarymanagementsystem.repository.BookRepository;
import com.pinarehedli.springlibrarymanagementsystem.repository.BorrowingRepository;
import com.pinarehedli.springlibrarymanagementsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowingService {

	private final BorrowingRepository borrowingRepository;
	private final UserRepository userRepository;
	private final BookRepository bookRepository;


	public List<BorrowingDTO> getUserAllBorrowings(String username) {
		return getBorrowingDTOS(username);

	}

	public List<BorrowingDTO> getAllBorrowingsByUsername(String username) {

		return getBorrowingDTOS(username);

	}

	private List<BorrowingDTO> getBorrowingDTOS(String username) {
		User user = userRepository
				.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		List<Borrowing> borrowings = borrowingRepository.findAllByUserId(user.getId());
		List<BorrowingDTO> dtoList = new ArrayList<>();

		for (Borrowing borrowing : borrowings) {
			if (borrowing.getUser().getId().equals(user.getId())) {
				dtoList.add(BorrowingMapper.toDTO(borrowing));
			}
		}

		return dtoList;
	}

	@Transactional
	public void borrow(BorrowRequestDTO dto, String username) {

		User user = userRepository
				.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Book book = bookRepository
				.findById(dto.getBookId())
				.orElseThrow(() -> new ResourceNotFoundException("Book not found"));

		if (book.getStock() > 0) {
			book.setStock(book.getStock() - 1);
			bookRepository.save(book);

			Borrowing borrowingBook = new Borrowing();
			borrowingBook.setBook(book);
			borrowingBook.setUser(user);
			borrowingBook.setBorrowDate(LocalDate.now());
			borrowingBook.setReturnDate(LocalDate.now().plusWeeks(1));
			borrowingBook.setReturned(false);

			borrowingRepository.save(borrowingBook);

		} else {
			throw new BookOutOfStockException("Book '" + book.getTitle() + "' is out of stock");
		}
	}

	@Transactional
	public void returnBook(ReturnRequestDTO dto, String username) {

		User foundUser = userRepository
				.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Borrowing borrowing = borrowingRepository
				.findById(dto.getBorrowingId())
				.orElseThrow(() -> new RuntimeException("ID not found"));

		if (foundUser.getId().equals(borrowing.getUser().getId())) {
			if (!borrowing.getReturned()) {
				if (!borrowing.getReturnDate().equals(LocalDate.now())) {

					User user = borrowing.getUser();

					int delay = Period.between(borrowing.getReturnDate(), LocalDate.now()).getDays();

					BigDecimal fineAmount = user.getFine().add(BigDecimal.valueOf(delay * 2L));

					BigDecimal userBalance = user.getBalance();

					if (userBalance.compareTo(fineAmount) >= 0) {
						user.setBalance(userBalance.subtract(fineAmount));
					} else {
						user.setBalance(BigDecimal.valueOf(0.0));
						user.setFine(fineAmount.subtract(userBalance));
					}
					userRepository.save(user);
				}
			} else {
				throw new ResourceAlreadyExistsException("Book already returned");
			}

			Book book = borrowing.getBook();

			book.setStock(book.getStock() + 1);

			bookRepository.save(book);
			borrowing.setReturned(true);

			borrowingRepository.save(borrowing);

		}
	}


}
