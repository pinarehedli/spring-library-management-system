package com.pinarehedli.springlibrarymanagementsystem.controller;

import com.pinarehedli.springlibrarymanagementsystem.model.dto.role.RoleDTO;
import com.pinarehedli.springlibrarymanagementsystem.model.entity.Role;
import com.pinarehedli.springlibrarymanagementsystem.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/roles")
public class RoleController {

	private final RoleService roleService;

	@GetMapping()
	public ResponseEntity<List<RoleDTO>> getAllRoles() {
		return ResponseEntity.ok(roleService.getAllRoles());
	}

	@PostMapping()
	public ResponseEntity<Role> createRole(@RequestBody Role role) {
		return new ResponseEntity<>(roleService.createRole(role), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role role) {
		return ResponseEntity.ok(roleService.updateRole(id, role));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
		roleService.deleteRole(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
