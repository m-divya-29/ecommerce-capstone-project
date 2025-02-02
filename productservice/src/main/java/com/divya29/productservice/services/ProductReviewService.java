package com.divya29.productservice.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.divya29.productservice.dtos.ProductReviewDto;
import com.divya29.productservice.models.Product;
import com.divya29.productservice.models.ProductReview;
import com.divya29.productservice.repositories.ProductReviewRepository;

@Service
public class ProductReviewService implements IProductReviewService {
	private final ProductReviewRepository reviewRepository;
	private final IProductService productService;
	private final ProductReviewMapper productReviewMapper;

	public ProductReviewService(ProductReviewRepository reviewRepository, IProductService productService,
			ProductReviewMapper productReviewMapper) {
		this.reviewRepository = reviewRepository;
		this.productService = productService;
		this.productReviewMapper = productReviewMapper;
	}

	@Override
	public ProductReviewDto createReview(ProductReviewDto reviewDto) throws NotFoundException {

		ProductReview review = productReviewMapper.toProductReview(reviewDto);
		return ProductReviewDto.fromProductReview(reviewRepository.save(review));
	}

	@Override
	public ProductReviewDto getReviewById(Long id) throws IllegalStateException {
		return reviewRepository.findById(id).map(ProductReviewDto::fromProductReview)
				.orElseThrow(() -> new IllegalStateException("Review not found"));
	}

	@Override
	public List<ProductReviewDto> getAllReviews() {
		return reviewRepository.findAll().stream().map(ProductReviewDto::fromProductReview).toList();
	}

	@Override
	public ProductReviewDto updateReview(Long id, ProductReviewDto reviewDto) throws IllegalStateException {
		if (!reviewRepository.existsById(id)) {
			throw new IllegalStateException("Review not found");
		}
		ProductReview review = productReviewMapper.toProductReview(reviewDto);
		;
		review.setId(id);
		return ProductReviewDto.fromProductReview(reviewRepository.save(review));
	}

	@Override
	public void deleteReview(Long id) throws IllegalStateException {
		if (!reviewRepository.existsById(id)) {
			throw new IllegalStateException("Review not found");
		}
		reviewRepository.deleteById(id);
	}

	@Override
	public List<ProductReviewDto> getReviewsByProduct(Long productId) throws IllegalStateException {
		// Ensure the product exists
		Optional<Product> product = productService.getSingleProduct(productId);
		if (product.isEmpty()) {
			throw new IllegalStateException("Product not found");
		}

		// Fetch reviews using repository method
		List<ProductReview> reviews = reviewRepository.findByProductId(productId);

		// Convert to DTOs
		return reviews.stream().map(ProductReviewDto::fromProductReview).collect(Collectors.toList());
	}
}
