package com.divya29.productservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.divya29.productservice.models.ProductReview;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    // You can add custom queries if needed
    List<ProductReview> findByProductId(Long productId);
}