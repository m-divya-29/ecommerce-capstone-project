package com.divya29.productservice.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.divya29.productservice.models.ProductSpecification;

@Repository
public interface ProductSpecificationESRepository extends ElasticsearchRepository<ProductSpecification, Long> {
}