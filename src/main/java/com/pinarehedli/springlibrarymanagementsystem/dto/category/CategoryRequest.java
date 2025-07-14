package com.pinarehedli.springlibrarymanagementsystem.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {

	@NotBlank
	private String name;
}
