package com.divya29.productservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.divya29.productservice.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // JpaRepository provides save, saveAll, findAll, findById, deleteById and other methods by default
    //    List<Product> findAll();
    //    Optional<Product> findById(@NonNull Long productId);
    //    Product save(Product product);
    //    void deleteById(@NonNull Long productId);

    Page<Product> findAll(Pageable pageable);
}
