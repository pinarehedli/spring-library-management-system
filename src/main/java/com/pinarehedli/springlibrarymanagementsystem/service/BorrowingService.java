package com.pinarehedli.springlibrarymanagementsystem.service;

import com.pinarehedli.springlibrarymanagementsystem.dto.borrowing.BorrowRequestDTO;
import com.pinarehedli.springlibrarymanagementsystem.dto.borrowing.BorrowingDTO;
import com.pinarehedli.springlibrarymanagementsystem.dto.borrowing.ReturnRequestDTO;
import com.pinarehedli.springlibrarymanagementsystem.entity.Book;
import com.pinarehedli.springlibrarymanagementsystem.entity.Borrowing;
import com.pinarehedli.springlibrarymanagementsystem.entity.User;
import com.pinarehedli.springlibrarymanagementsystem.exception.BookNotFoundException;
import com.pinarehedli.springlibrarymanagementsystem.exception.BookOutOfStockException;
import com.pinarehedli.springlibrarymanagementsystem.exception.InsufficientBalanceException;
import com.pinarehedli.springlibrarymanagementsystem.exception.UserNotFoundException;
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
				.orElseThrow(() -> new UserNotFoundException("User not found"));

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
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		Book book = bookRepository
				.findById(dto.getBookId())
				.orElseThrow(() -> new BookNotFoundException("Book not found"));

		if (book.getStock() > 0) {
			if (user.getBalance().compareTo(book.getPrice()) >= 0) {
				book.setStock(book.getStock() - 1);
				user.setBalance(user.getBalance().subtract(book.getPrice()));

				userRepository.save(user);
				bookRepository.save(book);

				Borrowing borrowingBook = new Borrowing();
				borrowingBook.setBook(book);
				borrowingBook.setUser(user);
				borrowingBook.setBorrowDate(LocalDate.now());
				borrowingBook.setReturnDate(LocalDate.now().plusWeeks(2));
				borrowingBook.setReturned(false);

				borrowingRepository.save(borrowingBook);

			} else {
				throw new InsufficientBalanceException("Balance is not enough");
			}
		} else {
			throw new BookOutOfStockException("Book '" + book.getTitle() + "' is out of stock");
		}
	}

	public void returnBook(ReturnRequestDTO dto) {

		Borrowing borrowing = borrowingRepository
				.findById(dto.getBorrowingId())
				.orElseThrow(() -> new RuntimeException("ID not found"));

		if (!borrowing.getReturnDate().equals(LocalDate.now())) {

			User user = borrowing.getUser();

			int delay = Period.between(LocalDate.now(), borrowing.getReturnDate()).getDays();

			BigDecimal fineAmount = BigDecimal.valueOf(delay * 2L);

			if (user.getBalance().compareTo(fineAmount) >= 0) {

				user.setBalance(user.getBalance().subtract(fineAmount));
				System.out.println(user.getBalance());
				userRepository.save(user);

			} else {
				throw new InsufficientBalanceException("Balance is not enough to pay fine");
			}
		}

		Book book = borrowing.getBook();

		book.setStock(book.getStock() + 1);

		bookRepository.save(book);
		borrowing.setReturned(true);

		borrowingRepository.save(borrowing);

	}
}
