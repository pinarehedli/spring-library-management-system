package com.pinarehedli.springlibrarymanagementsystem.mapper;

import com.pinarehedli.springlibrarymanagementsystem.dto.role.RoleDTO;
import com.pinarehedli.springlibrarymanagementsystem.entity.Role;

public class RoleMapper {

	public static RoleDTO toDTO(Role role) {
		return RoleDTO.builder()
				.id(role.getId())
				.name(role.getName())
				.build();

	}
}
