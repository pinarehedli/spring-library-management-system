package com.pinarehedli.springlibrarymanagementsystem.model.request.user;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceRequest {

	@NotNull
	@DecimalMin("0.0")
	private BigDecimal balance;

	@NotBlank
	private String username;
}
