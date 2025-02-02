package com.divya29.productservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.divya29.productservice.dtos.CategoryRequestDto;
import com.divya29.productservice.models.Category;
import com.divya29.productservice.models.Product;

public interface ICategoryService {

	List<Category> getAllCategories();

	Optional<Category> getCategoryById(Long id);

	Category createCategory(CategoryRequestDto categoryDto);

	Category updateCategory(Long id, CategoryRequestDto categoryDto) throws NotFoundException;

	void deleteCategory(Long id);

	//
//    Optional<Category> getCategory(Long categoryId);
//
//    Category addCategory(Category category);
//    Category updateCategory(Long categoryId, Category category);
//
//    Category deleteCategory(Long categoryId);
//
	List<Product> getProductsInCategory(String categoryName) throws NotFoundException;

}
