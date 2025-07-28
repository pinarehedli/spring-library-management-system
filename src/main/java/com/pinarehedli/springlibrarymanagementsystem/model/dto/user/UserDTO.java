package com.pinarehedli.springlibrarymanagementsystem.model.dto.user;

import com.pinarehedli.springlibrarymanagementsystem.model.dto.role.RoleDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class UserDTO {

	private Long id;
	private String username;
	private BigDecimal fine;
	private BigDecimal balance;
	private Set<RoleDTO> roles;
}

