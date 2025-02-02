package com.divya29.productservice.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.divya29.productservice.clients.fakestoreapi.FakeStoreClient;
import com.divya29.productservice.clients.fakestoreapi.FakeStoreProductDto;
import com.divya29.productservice.models.Product;

@Service(value = "fakeStoreProductService")
public class FakeStoreProductService {
	private static final Logger logger = LoggerFactory.getLogger(FakeStoreProductService.class);
	private FakeStoreClient fakeStoreClient;

	private RedisTemplate<String, Object> redisTemplate;

	public FakeStoreProductService(FakeStoreClient fakeStoreClient, RedisTemplate<String, Object> redisTemplate) {
		this.fakeStoreClient = fakeStoreClient;
		this.redisTemplate = redisTemplate;
	}

	public List<Product> getAllProducts() {

		try {
			List<FakeStoreProductDto> fakeStoreProductDtos = fakeStoreClient.getAllProducts();

			return fakeStoreProductDtos.stream().map(FakeStoreProductDto::toProduct).collect(Collectors.toList());

		} catch (Exception e) {
			logger.error("Error fetching products: {}", e.getMessage(), e);
			throw e;
		}

	}

	public Optional<Product> getSingleProduct(Long productId) {

		try {
			// Return data from cache if available
			FakeStoreProductDto fakeStoreProductDtoCache = (FakeStoreProductDto) redisTemplate.opsForHash()
					.get("PRODUCTS", productId);

			if (fakeStoreProductDtoCache != null) {
				return Optional.of(fakeStoreProductDtoCache.toProduct());
			}
			// Query the fakestore client to fetch the product
			Optional<FakeStoreProductDto> fakeStoreProductDto = fakeStoreClient.getSingleProduct(productId);

			if (fakeStoreProductDto.isPresent()) {
				redisTemplate.opsForHash().put("PRODUCTS", productId, fakeStoreProductDto.get());
			}

			return fakeStoreProductDto.map(FakeStoreProductDto::toProduct);

		} catch (Exception e) {
			logger.error("Error fetching product with id {}: {}", productId, e.getMessage(), e);
			return Optional.empty();
		}

	}

	public Product addProduct(Product product) {

		try {
			ResponseEntity<FakeStoreProductDto> response = fakeStoreClient.addNewProduct(product);
			FakeStoreProductDto addedProductDto = response.getBody();
			return addedProductDto.toProduct(); // Assuming toProduct() converts DTO to Product
		} catch (Exception e) {
			logger.error("Error adding product: {}", e.getMessage(), e);
			throw e;
		}

	}

	public Product updateProduct(Long productId, Product product) {

		try {
			ResponseEntity<FakeStoreProductDto> response = fakeStoreClient.updateProduct(productId, product);
			FakeStoreProductDto updatedProductDto = response.getBody();
			return updatedProductDto.toProduct(); // converts DTO to Product
		} catch (Exception e) {
			logger.error("Error updating product with id {}: {}", productId, e.getMessage(), e);
			throw e;
		}
	}

	public Product replaceProduct(Long productId, Product product) {

		try {
			ResponseEntity<FakeStoreProductDto> response = fakeStoreClient.replaceProduct(productId, product);
			FakeStoreProductDto fakeStoreProductDto = response.getBody();
			return fakeStoreProductDto.toProduct(); // Assuming toProduct() converts DTO to Product
		} catch (Exception e) {
			logger.error("Error replacing product with id {}: {}", productId, e.getMessage(), e);
			throw e;
		}

	}

	public Product deleteProduct(Long productId) {

		try {
			ResponseEntity<FakeStoreProductDto> response = fakeStoreClient.deleteProduct(productId);
			FakeStoreProductDto fakeStoreProductDto = response.getBody();
			return fakeStoreProductDto.toProduct(); // Assuming toProduct() converts DTO to Product
		} catch (Exception e) {
			logger.error("Error replacing product with id {}: {}", productId, e.getMessage(), e);
			throw e;
		}

	}
}
