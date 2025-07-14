package com.pinarehedli.springlibrarymanagementsystem.dto.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {

	private Long id;

	private String name;
}

