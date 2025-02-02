package com.divya29.productservice.controllers;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divya29.productservice.dtos.ProductReviewDto;
import com.divya29.productservice.services.IProductReviewService;

@RestController
@RequestMapping("/reviews")
public class ProductReviewController {

	private final IProductReviewService reviewService;

	public ProductReviewController(IProductReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@PostMapping
	public ResponseEntity<ProductReviewDto> createReview(@RequestBody ProductReviewDto reviewDto)
			throws NotFoundException {
		return ResponseEntity.ok(reviewService.createReview(reviewDto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductReviewDto> getReviewById(@PathVariable Long id) throws NotFoundException {
		return ResponseEntity.ok(reviewService.getReviewById(id));
	}

	@GetMapping
	public ResponseEntity<List<ProductReviewDto>> getAllReviews() {
		return ResponseEntity.ok(reviewService.getAllReviews());
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductReviewDto> updateReview(@PathVariable Long id, @RequestBody ProductReviewDto reviewDto)
			throws NotFoundException {
		return ResponseEntity.ok(reviewService.updateReview(id, reviewDto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteReview(@PathVariable Long id) throws NotFoundException {
		reviewService.deleteReview(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<List<ProductReviewDto>> getReviewsByProduct(@PathVariable Long productId)
			throws NotFoundException, NotFoundException {
		return ResponseEntity.ok(reviewService.getReviewsByProduct(productId));
	}
}