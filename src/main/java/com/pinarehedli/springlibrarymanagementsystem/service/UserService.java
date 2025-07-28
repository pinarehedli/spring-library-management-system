package com.pinarehedli.springlibrarymanagementsystem.service;

import com.pinarehedli.springlibrarymanagementsystem.exception.ResourceNotFoundException;
import com.pinarehedli.springlibrarymanagementsystem.model.request.user.BalanceRequest;
import com.pinarehedli.springlibrarymanagementsystem.model.dto.user.UserDTO;
import com.pinarehedli.springlibrarymanagementsystem.model.entity.Role;
import com.pinarehedli.springlibrarymanagementsystem.model.entity.User;
import com.pinarehedli.springlibrarymanagementsystem.mapper.UserMapper;
import com.pinarehedli.springlibrarymanagementsystem.repository.RoleRepository;
import com.pinarehedli.springlibrarymanagementsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	public UserDTO getUserProfile(String username) {
		User user = userRepository
				.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		return UserMapper.toDTO(user);
	}

	public BigDecimal addBalance(BalanceRequest request) {
		User user = userRepository
				.findByUsername(request.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		BigDecimal fine = user.getFine();
		BigDecimal balance = user.getBalance().add(request.getBalance());

		if (balance.compareTo(fine) >= 0) {
			user.setBalance(balance.subtract(fine));
			user.setFine(BigDecimal.valueOf(0.0));
		} else {
			user.setBalance(BigDecimal.valueOf(0.0));
			user.setFine(fine.subtract(balance));
		}

		userRepository.save(user);

		return user.getBalance();
	}

	@Transactional
	public void addRoleToUser(String username, String roleName) {
		User user = userRepository
				.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Role role = roleRepository
				.getRoleByName(roleName)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found"));

		user.getRoles().add(role);
		userRepository.save(user);
	}

	@Transactional
	public void removeRoleFromUser(String username, String roleName) {
		User user = userRepository
				.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Role role = roleRepository
				.getRoleByName(roleName)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found"));

		user.getRoles().remove(role);
		userRepository.save(user);
	}

	@Transactional
	public User register(User user) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		if (user.getRoles() == null || user.getRoles().isEmpty()) {
			Role role = roleRepository
					.getRoleByName("USER")
					.orElseThrow(() -> new ResourceNotFoundException("Role not found"));

			user.getRoles().add(role);
		}

		return userRepository.save(user);


	}
}
