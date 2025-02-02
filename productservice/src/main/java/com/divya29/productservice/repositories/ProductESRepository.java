package com.divya29.productservice.repositories;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.divya29.productservice.models.Product;

@Repository
public interface ProductESRepository extends ElasticsearchRepository<Product, Long> {
    List<Product> findAllByTitleContaining(String query);
}
