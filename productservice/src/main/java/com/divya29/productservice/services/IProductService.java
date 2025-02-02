package com.divya29.productservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;

import com.divya29.productservice.models.Product;
import com.divya29.productservice.models.SortParam;

public interface IProductService {

	Page<Product> getProducts(int numOfResults, int offset, List<SortParam> sortParams);

	List<Product> getAllProducts();

	Optional<Product> getSingleProduct(Long productId);

	Product addProduct(Product product);

	Product updateProduct(Long productId, Product product) throws NotFoundException;

	Product replaceProduct(Long productId, Product product) throws NotFoundException;

	Product deleteProduct(Long productId) throws NotFoundException;

	List<Product> searchProducts(String keyword);
}