package com.pinarehedli.springlibrarymanagementsystem.dto.user;

import com.pinarehedli.springlibrarymanagementsystem.dto.role.RoleDTO;
import com.pinarehedli.springlibrarymanagementsystem.entity.Role;
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

