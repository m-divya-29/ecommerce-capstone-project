package com.divya29.productservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.divya29.productservice.dtos.ProductResponseDto;
import com.divya29.productservice.dtos.utils.ESUtils;
import com.divya29.productservice.models.Category;
import com.divya29.productservice.models.Product;
import com.divya29.productservice.models.SortParam;
import com.divya29.productservice.models.SortType;
import com.divya29.productservice.repositories.CategoryRepository;
import com.divya29.productservice.repositories.ProductESRepository;
import com.divya29.productservice.repositories.ProductRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Primary
public class SelfProductService implements IProductService {
	private ProductRepository productRepository;
	private CategoryRepository categoryRepository;

	private final ProductESRepository productESRepository;
	private RedisTemplate<String, Object> redisTemplate;

	private Category getOrSetCategory(Category category) {
		if (category != null) {
			Category savedCategory = categoryRepository.findByName(category.getName())
					.orElseGet(() -> categoryRepository.save(category));
			return savedCategory;

		}
		return null;
	}

	@Override
	public Page<Product> getProducts(int numOfResults, int offset, List<SortParam> sortParams) {
		Sort sort;
		if (sortParams.get(0).getSortType().equals(SortType.ASC)) {
			sort = Sort.by(sortParams.get(0).getParamName()).ascending();
		} else {
			sort = Sort.by(sortParams.get(0).getParamName()).descending();
		}

		for (int i = 1; i < sortParams.size(); i++) {
			if (sortParams.get(i).getSortType().equals(SortType.ASC)) {
				sort.and(Sort.by(sortParams.get(i).getParamName()).ascending());
			} else {
				sort = sort.and(Sort.by(sortParams.get(i).getParamName()).descending());

			}
		}

		return productRepository.findAll(PageRequest.of((offset / numOfResults), numOfResults, sort));
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Optional<Product> getSingleProduct(Long productId) {
		ProductResponseDto responseDto = (ProductResponseDto) redisTemplate.opsForHash().get("PRODUCTS", productId);
		if (responseDto != null) {
			return Optional.of(responseDto.toProduct());
		}
		// Query the repository fetch the product
		Optional<Product> productOptional = productRepository.findById(productId);

		if (productOptional.isPresent()) {
			redisTemplate.opsForHash().put("PRODUCTS", productId,
					ProductResponseDto.fromProduct(productOptional.get()));
		}

		return productOptional;

	}

	@Override
	public Product addProduct(Product product) {
		product.setCategory(getOrSetCategory(product.getCategory()));
		Product newProduct = productRepository.save(product);
		ESUtils.mapForES(Product.class, newProduct).map(res -> productESRepository.save(res));
		return newProduct;
	}

	@Override
	public Product updateProduct(Long productId, Product product) throws IllegalStateException {
		product.setCategory(getOrSetCategory(product.getCategory()));

		Optional<Product> existingProductOptional = getSingleProduct(productId);
		if (existingProductOptional.isEmpty()) {
			throw new IllegalStateException("Product with id " + productId + " not found.");
		}

		Product existingProduct = existingProductOptional.get();
		if (product.getId() != null) {
			existingProduct.setId(productId);
		}
		if (product.getImageUrl() != null) {
			existingProduct.setImageUrl(product.getImageUrl());
		}
		if (product.getTitle() != null) {
			existingProduct.setTitle(product.getTitle());
		}
		if (product.getDescription() != null) {
			existingProduct.setDescription(product.getDescription());
		}
		if (product.getPrice() != null) {
			existingProduct.setPrice(product.getPrice());
		}
		if (product.getCategory() != null) {
			existingProduct.setCategory(product.getCategory());
		}
		if (product.getStockQuantity() != null) {
			existingProduct.setStockQuantity(product.getStockQuantity());
		}
		return productRepository.save(existingProduct);

	}

	@Override
	public Product replaceProduct(Long productId, Product product) throws IllegalStateException {
		product.setCategory(getOrSetCategory(product.getCategory()));

		Optional<Product> existingProductOptional = getSingleProduct(productId);
		if (existingProductOptional.isEmpty()) {
			throw new IllegalStateException("Product with id " + productId + " not found.");
		}

		Product existingProduct = existingProductOptional.get();
		// Update fields
		existingProduct.setId(productId);
		existingProduct.setImageUrl(product.getImageUrl());
		existingProduct.setTitle(product.getTitle());
		existingProduct.setDescription(product.getDescription());
		existingProduct.setPrice(product.getPrice());
		existingProduct.setCategory(product.getCategory());
		existingProduct.setStockQuantity(product.getStockQuantity());

		return productRepository.save(existingProduct);

	}

	@Override
	public Product deleteProduct(Long productId) throws IllegalStateException {
		Optional<Product> existingProductOptional = getSingleProduct(productId);
		if (existingProductOptional.isEmpty()) {
			throw new IllegalStateException("Product with id " + productId + " not found.");
		}

		productRepository.deleteById(productId);
		productESRepository.deleteById(productId);
		return existingProductOptional.get();
	}

	@Override
	public List<Product> searchProducts(String keyword) {
		return productESRepository.findAllByTitleContaining(keyword);

	}
}
