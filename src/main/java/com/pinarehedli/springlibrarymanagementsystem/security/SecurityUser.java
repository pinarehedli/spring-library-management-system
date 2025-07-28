package com.pinarehedli.springlibrarymanagementsystem.security;

import com.pinarehedli.springlibrarymanagementsystem.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@AllArgsConstructor
public class SecurityUser implements UserDetails {

	private User user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
				.toList();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
}
