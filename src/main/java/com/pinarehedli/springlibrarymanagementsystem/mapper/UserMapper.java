package com.pinarehedli.springlibrarymanagementsystem.mapper;

import com.pinarehedli.springlibrarymanagementsystem.dto.auth.RegisterRequest;
import com.pinarehedli.springlibrarymanagementsystem.dto.user.UserDTO;
import com.pinarehedli.springlibrarymanagementsystem.entity.Role;
import com.pinarehedli.springlibrarymanagementsystem.entity.User;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

	public static UserDTO toDTO(User user) {

		return UserDTO.builder()
				.id(user.getId())
				.username(user.getUsername())
				.balance(user.getBalance())
				.fine(user.getFine())
				.roles(user
						.getRoles()
						.stream()
						.map(RoleMapper::toDTO)
						.collect(Collectors.toSet()))
				.build();
	}

	public static User toEntityFromRequest(RegisterRequest request, Role role) {
		return User.builder()
				.username(request.getUsername())
				.password(request.getPassword())
				.roles(Set.of(role))
				.balance(BigDecimal.valueOf(0.0))
				.fine(BigDecimal.valueOf(0.0))
				.build();
	}
}
