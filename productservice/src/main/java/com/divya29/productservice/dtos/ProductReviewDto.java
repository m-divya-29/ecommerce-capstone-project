package com.divya29.productservice.dtos;

import com.divya29.productservice.models.ProductReview;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewDto {
    private Long id;
    private int rating;
    private String comment;
    private Long productId;
    private Long userId;

    public static ProductReviewDto fromProductReview(ProductReview review) {
        ProductReviewDto dto = new ProductReviewDto();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setProductId(review.getProduct().getId());
        dto.setUserId(review.getUserId());
        return dto;
    }

}
