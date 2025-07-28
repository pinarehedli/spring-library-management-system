package com.pinarehedli.springlibrarymanagementsystem.controller;

import com.pinarehedli.springlibrarymanagementsystem.model.request.user.BalanceRequest;
import com.pinarehedli.springlibrarymanagementsystem.model.dto.user.UserDTO;
import com.pinarehedli.springlibrarymanagementsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/users")
public class UserController {

	private final UserService userService;

	@GetMapping("/profile")
	public ResponseEntity<UserDTO> getUserProfile() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return ResponseEntity.ok(userService.getUserProfile(username));
	}

	@PostMapping("/balance")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addBalance(@Valid @RequestBody BalanceRequest request) {
		return ResponseEntity.ok("Current balance: " + userService.addBalance(request));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{username}/roles/{roleName}")
	public ResponseEntity<String> addRoleToUser(@PathVariable String username, @PathVariable String roleName) {
		userService.addRoleToUser(username, roleName);
		return ResponseEntity.ok("Role added to user successfully");
	}

	@PutMapping("/{username}/roles/{roleName}/remove")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> removeRoleFromUser(@PathVariable String username, @PathVariable String roleName) {
		userService.removeRoleFromUser(username, roleName);
		return ResponseEntity.ok("Role removed from user successfully");
	}






}
