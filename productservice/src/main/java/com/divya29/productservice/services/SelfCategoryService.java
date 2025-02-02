package com.divya29.productservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.divya29.productservice.dtos.CategoryRequestDto;
import com.divya29.productservice.models.Category;
import com.divya29.productservice.models.Product;
import com.divya29.productservice.repositories.CategoryRepository;

import lombok.AllArgsConstructor;

@Service
@Primary
@AllArgsConstructor
public class SelfCategoryService implements ICategoryService {
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Optional<Category> getCategoryById(Long id) {
		return categoryRepository.findById(id);
	}

	@Override
	public Category createCategory(CategoryRequestDto categoryDto) {
		Category category = new Category();
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		return categoryRepository.save(category);
	}

	@Override
	public Category updateCategory(Long id, CategoryRequestDto categoryDto) throws IllegalStateException {
		Optional<Category> optionalCategory = categoryRepository.findById(id);
		if (optionalCategory.isEmpty()) {
			throw new IllegalStateException("Category with id " + id + " not found.");
		}

		Category category = optionalCategory.get();
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		return categoryRepository.save(category);

	}

	@Override
	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
	}

	@Override
	public List<Product> getProductsInCategory(String categoryName) throws IllegalStateException {
		Optional<Category> categoryOptional = categoryRepository.findByName(categoryName);
		if (categoryOptional.isEmpty()) {
			throw new IllegalStateException("Category with name " + categoryName + " not found.");
		}
		Category category = categoryOptional.get();
		return category.getProducts();
	}

}
