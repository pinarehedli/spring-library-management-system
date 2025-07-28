package com.pinarehedli.springlibrarymanagementsystem.model.request.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {

	@NotBlank
	private String name;
}
