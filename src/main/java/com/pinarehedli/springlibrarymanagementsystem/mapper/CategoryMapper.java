package com.pinarehedli.springlibrarymanagementsystem.mapper;

import com.pinarehedli.springlibrarymanagementsystem.model.dto.category.CategoryDTO;
import com.pinarehedli.springlibrarymanagementsystem.model.request.category.CategoryRequest;
import com.pinarehedli.springlibrarymanagementsystem.model.entity.Category;

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
