
package com.divya29.productservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.divya29.productservice.dtos.ProductReviewDto;
import com.divya29.productservice.models.Product;
import com.divya29.productservice.models.ProductReview;

@Component
public class ProductReviewMapper {

	@Autowired
	private SelfProductService productService;

	public ProductReview toProductReview(ProductReviewDto dto) {
		ProductReview review = new ProductReview();
		review.setId(dto.getId());
		review.setRating(dto.getRating());
		review.setComment(dto.getComment());
		review.setUserId(dto.getUserId());

		// Fetch the product using the productId from the dto
		Product product = productService.getSingleProduct(dto.getProductId())
				.orElseThrow(() -> new RuntimeException("Product not found"));
		review.setProduct(product);

		return review;
	}
}
