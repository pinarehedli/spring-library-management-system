package com.pinarehedli.springlibrarymanagementsystem.service;

import com.pinarehedli.springlibrarymanagementsystem.dto.category.CategoryDTO;
import com.pinarehedli.springlibrarymanagementsystem.dto.category.CategoryRequest;
import com.pinarehedli.springlibrarymanagementsystem.entity.Category;
import com.pinarehedli.springlibrarymanagementsystem.exception.CategoryNotFoundException;
import com.pinarehedli.springlibrarymanagementsystem.mapper.CategoryMapper;
import com.pinarehedli.springlibrarymanagementsystem.repository.CategoryRepository;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public List<CategoryDTO> findAllCategories() {
		List<Category> categories = categoryRepository.findAll();

		return categories.stream()
				.map(CategoryMapper::toDTO)
				.toList();
	}


	public CategoryDTO findCategoryById(@Min(1) Long id) {
		Category category = categoryRepository
				.findById(id)
				.orElseThrow(() -> new CategoryNotFoundException("Category not found"));

		return CategoryMapper.toDTO(category);
	}

	public CategoryDTO addCategory(CategoryRequest request) {

		Category savedCategory = categoryRepository.save(CategoryMapper.fromRequest(request));

		return CategoryMapper.toDTO(savedCategory);

	}

	public CategoryDTO updateCategory(CategoryRequest request, Long id) {
		Category category = categoryRepository
				.findById(id)
				.orElseThrow(() -> new CategoryNotFoundException("Category not found"));

		category.setName(request.getName());

		Category savedCategory = categoryRepository.save(category);

		return CategoryMapper.toDTO(savedCategory);

	}

	public void deleteCategory(Long id) {
		Category category = categoryRepository
				.findById(id)
				.orElseThrow(() -> new CategoryNotFoundException("Category not found"));

		categoryRepository.delete(category);
	}
}
