package com.pinarehedli.springlibrarymanagementsystem.model.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDTO {

	@NotNull
	private Long id;

	@NotBlank
	private String name;
}
