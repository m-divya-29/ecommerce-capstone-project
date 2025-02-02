package com.divya29.productservice.services;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.divya29.productservice.dtos.ProductReviewDto;

public interface IProductReviewService {
	ProductReviewDto createReview(ProductReviewDto reviewDto) throws NotFoundException;

	ProductReviewDto getReviewById(Long id) throws NotFoundException;

	List<ProductReviewDto> getAllReviews();

	ProductReviewDto updateReview(Long id, ProductReviewDto reviewDto) throws NotFoundException;

	void deleteReview(Long id) throws NotFoundException;

	List<ProductReviewDto> getReviewsByProduct(Long productId) throws NotFoundException;
}
