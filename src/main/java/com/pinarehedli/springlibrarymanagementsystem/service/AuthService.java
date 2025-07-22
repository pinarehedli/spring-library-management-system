package com.pinarehedli.springlibrarymanagementsystem.service;

import com.pinarehedli.springlibrarymanagementsystem.dto.auth.AuthResponse;
import com.pinarehedli.springlibrarymanagementsystem.dto.auth.LoginRequest;
import com.pinarehedli.springlibrarymanagementsystem.dto.auth.RefreshTokenRequest;
import com.pinarehedli.springlibrarymanagementsystem.dto.auth.RegisterRequest;
import com.pinarehedli.springlibrarymanagementsystem.entity.Role;
import com.pinarehedli.springlibrarymanagementsystem.entity.User;
import com.pinarehedli.springlibrarymanagementsystem.exception.RoleNotFoundException;
import com.pinarehedli.springlibrarymanagementsystem.mapper.UserMapper;
import com.pinarehedli.springlibrarymanagementsystem.repository.RoleRepository;
import com.pinarehedli.springlibrarymanagementsystem.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final UserService userService;
	private final JwtService jwtService;
	private final RoleRepository roleRepository;

	public AuthResponse login(LoginRequest request) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
						request.getPassword()
				)
		);

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		String accessToken = jwtService.generateAccessToken(userDetails);
		String refreshToken = jwtService.generateRefreshToken(userDetails);

		return AuthResponse.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();

	}

	public AuthResponse register(RegisterRequest request) {

		Role role = roleRepository
				.getRoleByName("USER")
				.orElseThrow(() -> new RoleNotFoundException("Role not found"));

		User user = UserMapper.toEntityFromRequest(request, role);

		User registeredUser =  userService.register(user);

		UserDetails userDetails = userDetailsService.loadUserByUsername(registeredUser.getUsername());

		String accessToken = jwtService.generateAccessToken(userDetails);
		String refreshToken = jwtService.generateRefreshToken(userDetails);

		return AuthResponse.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}

	public AuthResponse refresh(RefreshTokenRequest request) {
		String refreshToken = request.getRefreshToken();

		String username = jwtService.extractUsername(refreshToken);

		if (username != null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			if (jwtService.isTokenValid(refreshToken, userDetails)) {
				String accessToken = jwtService.generateAccessToken(userDetails);

				return AuthResponse.builder()
						.accessToken(accessToken)
						.refreshToken(refreshToken)
						.build();
			}

		}

		return null;
	}
}
