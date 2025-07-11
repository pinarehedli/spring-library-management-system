package com.pinarehedli.springlibrarymanagementsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	@Column(unique = true)
	private String email;

	private String password;

	private BigDecimal balance;

	@OneToMany(mappedBy = "user")
	private List<Transaction> transactions;

	@OneToMany(mappedBy = "user")
	private List<Borrowing> borrowings;

}
