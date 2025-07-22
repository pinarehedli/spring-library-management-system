package com.pinarehedli.springlibrarymanagementsystem.service;

import com.pinarehedli.springlibrarymanagementsystem.dto.role.RoleDTO;
import com.pinarehedli.springlibrarymanagementsystem.entity.Role;
import com.pinarehedli.springlibrarymanagementsystem.exception.RoleNotFoundException;
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

	public Role getRoleById(Long id) {
		return roleRepository
				.findById(id)
				.orElseThrow(() -> new RoleNotFoundException("Role not found in id: " + id));
	}


	public Role createRole(Role role) {
		return roleRepository.save(role);
	}

	public Role updateRole(Long id, Role role) {
		Role existingRole = getRoleById(id);
		existingRole.setName(role.getName());
		return roleRepository.save(existingRole);
	}

	public void deleteRole(Long id) {
		Role role = getRoleById(id);
		roleRepository.delete(role);
	}
}
