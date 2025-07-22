package com.pinarehedli.springlibrarymanagementsystem.repository;

import com.pinarehedli.springlibrarymanagementsystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> getRoleByName(String user);
}
