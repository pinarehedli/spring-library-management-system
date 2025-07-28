package com.pinarehedli.springlibrarymanagementsystem.controller;

import com.pinarehedli.springlibrarymanagementsystem.model.dto.category.CategoryDTO;
import com.pinarehedli.springlibrarymanagementsystem.model.request.category.CategoryRequest;
import com.pinarehedli.springlibrarymanagementsystem.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/categories")
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping()
	public ResponseEntity<List<CategoryDTO>> findAllCategories() {
		return ResponseEntity.ok(categoryService.findAllCategories());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> findCategoryById(@Min(1) @PathVariable Long id) {
		return ResponseEntity.ok(categoryService.findCategoryById(id));
	}

	@PostMapping()
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
		return new ResponseEntity<>(categoryService.addCategory(categoryRequest), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryRequest request, @Min(1) @PathVariable Long id) {
		return new ResponseEntity<>(categoryService.updateCategory(request, id), HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCategory(@Min(1) @PathVariable Long id) {
		categoryService.deleteCategory(id);
		return ResponseEntity.ok("Category deleted successfully!");
	}
}
