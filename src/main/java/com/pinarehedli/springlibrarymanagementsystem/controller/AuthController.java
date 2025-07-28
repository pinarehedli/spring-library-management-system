package com.pinarehedli.springlibrarymanagementsystem.controller;

import com.pinarehedli.springlibrarymanagementsystem.model.response.AuthResponse;
import com.pinarehedli.springlibrarymanagementsystem.model.request.auth.LoginRequest;
import com.pinarehedli.springlibrarymanagementsystem.model.request.auth.RefreshTokenRequest;
import com.pinarehedli.springlibrarymanagementsystem.model.request.auth.RegisterRequest;
import com.pinarehedli.springlibrarymanagementsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/auth/")
public class AuthController {

	private final AuthService authService;

	@PostMapping(path = "/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}

	@PostMapping(path = "/register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
		return ResponseEntity.ok(authService.register(request));
	}

	@PostMapping(path = "/refresh")
	public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request) {
		return ResponseEntity.ok(authService.refresh(request));
	}
}
