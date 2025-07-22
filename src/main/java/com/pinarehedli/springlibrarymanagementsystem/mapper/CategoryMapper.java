package com.pinarehedli.springlibrarymanagementsystem.mapper;

import com.pinarehedli.springlibrarymanagementsystem.dto.category.CategoryDTO;
import com.pinarehedli.springlibrarymanagementsystem.dto.category.CategoryRequest;
import com.pinarehedli.springlibrarymanagementsystem.entity.Category;

public class CategoryMapper {

	public static CategoryDTO toDTO(Category category) {
		return CategoryDTO.builder()
				.id(category.getId())
				.name(category.getName())
				.build();

	}

	public static Category fromRequest(CategoryRequest request) {
		return Category.builder()
				.name(request.getName())
				.build();
	}
}
