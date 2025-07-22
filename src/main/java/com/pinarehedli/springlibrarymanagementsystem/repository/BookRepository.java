package com.pinarehedli.springlibrarymanagementsystem.repository;

import com.pinarehedli.springlibrarymanagementsystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findAllByCategoryId(Long categoryId);

}
