package com.divya29.productservice.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divya29.productservice.dtos.CategoryDto;
import com.divya29.productservice.dtos.CategoryRequestDto;
import com.divya29.productservice.dtos.ProductResponseDto;
import com.divya29.productservice.models.Category;
import com.divya29.productservice.models.Product;
import com.divya29.productservice.services.ICategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	ICategoryService categoryService;

	public CategoryController(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("")
	public ResponseEntity<List<CategoryDto>> getAllCategories() throws IllegalStateException {

		try {
			List<Category> categoryList = categoryService.getAllCategories();

			if (categoryList.isEmpty()) {
				throw new IllegalStateException("No categories found.");
			}

			logger.info("All categories: {}", categoryList);
			List<CategoryDto> categoryDtoList = categoryList.stream().map(CategoryDto::fromCategory)
					.collect(Collectors.toList());
			return new ResponseEntity<>(categoryDtoList, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error occured while adding category :{}.", e.getMessage(), e);
			throw e;
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) throws IllegalStateException {
		Category category = categoryService.getCategoryById(id)
				.orElseThrow(() -> new IllegalStateException("Category not found with id: " + id));
		return ResponseEntity.ok(CategoryDto.fromCategory(category));
	}

	@PostMapping
	public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryRequestDto categoryDto) {
		try {
			Category createdCategory = categoryService.createCategory(categoryDto);
			return new ResponseEntity<>(CategoryDto.fromCategory(createdCategory), HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Error occured while getting products in category :{}.", e.getMessage(), e);
			throw e;
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id,
			@RequestBody CategoryRequestDto categoryDto) throws IllegalStateException, NotFoundException {
		Category updatedCategory = categoryService.updateCategory(id, categoryDto);
		return ResponseEntity.ok(CategoryDto.fromCategory(updatedCategory));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
		categoryService.deleteCategory(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{categoryName}/products")
	public ResponseEntity<List<ProductResponseDto>> getProductsByCategory(
			@PathVariable("categoryName") String categoryName) throws Exception {
		try {
			List<Product> products = categoryService.getProductsInCategory(categoryName);
			List<ProductResponseDto> responseDtos = products.stream().map(ProductResponseDto::fromProduct)
					.collect(Collectors.toList());
			return new ResponseEntity<>(responseDtos, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error occured while getting products in category :{}.", e.getMessage(), e);
			throw e;
		}
	}

}
