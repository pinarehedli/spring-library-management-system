package com.pinarehedli.springlibrarymanagementsystem.repository;

import com.pinarehedli.springlibrarymanagementsystem.model.entity.Book;
import org.hibernate.validator.constraints.ISBN;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findAllByCategoryId(Long categoryId);

	Optional<Book> findBookByIsbn(String isbn);
}
