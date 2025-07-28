package com.pinarehedli.springlibrarymanagementsystem.service;

import com.pinarehedli.springlibrarymanagementsystem.exception.ResourceNotFoundException;
import com.pinarehedli.springlibrarymanagementsystem.model.dto.role.RoleDTO;
import com.pinarehedli.springlibrarymanagementsystem.model.entity.Role;
import com.pinarehedli.springlibrarymanagementsystem.mapper.RoleMapper;
import com.pinarehedli.springlibrarymanagementsystem.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

	private final RoleRepository roleRepository;

	public List<RoleDTO> getAllRoles() {
		List<Role> roles = roleRepository.findAll();

		return roles.stream()
				.map(RoleMapper::toDTO)
				.toList();
	}


	public Role createRole(Role role) {
		return roleRepository.save(role);
	}

	public Role updateRole(Long id, Role role) {
		Role existingRole = roleRepository
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found"));

		existingRole.setName(role.getName());
		return roleRepository.save(existingRole);
	}

	public void deleteRole(Long id) {
		Role role = roleRepository
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found"));

		roleRepository.delete(role);
	}
}
