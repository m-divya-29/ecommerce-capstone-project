package com.divya29.productservice.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.divya29.productservice.models.ProductSpecification;

@Repository
public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification, Long> {
    List<ProductSpecification> findByProductId(Long productId);

    // Query to search by attribute name and value
    List<ProductSpecification> findByAttributeNameAndAttributeValue(String attributeName, String attributeValue);
}