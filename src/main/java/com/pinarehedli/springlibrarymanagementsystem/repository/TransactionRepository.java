package com.pinarehedli.springlibrarymanagementsystem.repository;

import com.pinarehedli.springlibrarymanagementsystem.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> getTransactionsByUserId(Long userId);

}
