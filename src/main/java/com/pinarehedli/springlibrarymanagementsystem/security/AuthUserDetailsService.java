package com.pinarehedli.springlibrarymanagementsystem.security;

import com.pinarehedli.springlibrarymanagementsystem.entity.User;
import com.pinarehedli.springlibrarymanagementsystem.exception.UserNotFoundException;
import com.pinarehedli.springlibrarymanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository
				.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		return new SecurityUser(user);
	}

}
