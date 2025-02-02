package com.divya29.productservice.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divya29.productservice.dtos.GetProductsRequestDto;
import com.divya29.productservice.dtos.ProductDto;
import com.divya29.productservice.dtos.ProductResponseDto;
import com.divya29.productservice.models.Product;
import com.divya29.productservice.services.IProductService;

import io.micrometer.common.lang.Nullable;

@RestController
@RequestMapping("/products")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	private IProductService productService;

	public ProductController(IProductService productService) {
		this.productService = productService;
	}

	@PostMapping("/paginated")
	public ResponseEntity<Page<ProductResponseDto>> getProducts(@RequestBody GetProductsRequestDto requestDto) {
		Page<Product> productPage = productService.getProducts(requestDto.getNumOfResults(), requestDto.getOffset(),
				requestDto.getSortParamsList());
		Page<ProductResponseDto> productResponseDtos = productPage.map(ProductResponseDto::fromProduct);

		return new ResponseEntity<>(productResponseDtos, HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<List<ProductResponseDto>> getAllProducts(@Nullable @RequestHeader("AUTH_TOKEN") String token,
			@Nullable @RequestHeader("USER_ID") Long userId) throws IllegalStateException {
		try {
			// Fetch the products
			List<Product> productList = productService.getAllProducts();

			if (productList.isEmpty()) {
				throw new IllegalStateException("No products found.");
			}

			logger.info("All products: {}", productList);
			List<ProductResponseDto> responseDtosList = productList.stream().map(ProductResponseDto::fromProduct)
					.collect(Collectors.toList());

			return new ResponseEntity<>(responseDtosList, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error occured while adding product :{}.", e.getMessage(), e);
			throw e;
		}
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductResponseDto> getSingleProduct(@PathVariable("productId") Long productId)
			throws IllegalStateException {

		try {
			Optional<Product> productOptional = productService.getSingleProduct(productId);
			if (productOptional.isEmpty()) {
				logger.error("Product with id {} not found.", productId);
				throw new IllegalStateException("Product with id " + productId + " not found.");
			}
			Product product = productOptional.get();
			logger.info("Get product: {}", product);
			ProductResponseDto productResponseDto = ProductResponseDto.fromProduct(product);
			return new ResponseEntity<>(productResponseDto, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error occured while adding product :{}.", e.getMessage(), e);
			throw e;
		}

	}

	@PostMapping("")
	public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductDto productDto) throws Exception {

		try {
			Product product = productDto.toProduct();
			Product addedProduct = productService.addProduct(product);
			logger.info("Added new product: {}", addedProduct);
			ProductResponseDto productResponseDto = ProductResponseDto.fromProduct(addedProduct);
			return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Error occured while adding product :{}.", e.getMessage(), e);
			throw e;
		}
	}

	@PatchMapping("/{productId}")
	public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("productId") Long productId,
			@RequestBody ProductDto productDto) throws Exception {

		try {
			Product product = productDto.toProduct();
			Product savedProduct = productService.updateProduct(productId, product);
			logger.info("Updated product: {}", savedProduct);
			ProductResponseDto productResponseDto = ProductResponseDto.fromProduct(savedProduct);
			return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
		} catch (IllegalStateException e) {
			logger.error("Product with id {} not found.", productId);
			throw e;
		} catch (Exception e) {
			logger.error("Error occured while updating product :{}.", e.getMessage(), e);
			throw e;
		}

	}

	@PutMapping("/{productId}")
	public ResponseEntity<ProductResponseDto> replaceProduct(@PathVariable("productId") Long productId,
			@RequestBody ProductDto productDto) throws Exception {

		try {
			Product product = productDto.toProduct();
			Product savedProduct = productService.replaceProduct(productId, product);
			logger.info("Replaced product: {}", savedProduct);
			ProductResponseDto productResponseDto = ProductResponseDto.fromProduct(savedProduct);
			return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
		} catch (IllegalStateException e) {
			logger.error("Product with id {} not found.", productId);
			throw e;
		} catch (Exception e) {
			logger.error("Error occured while replacing product :{}.", e.getMessage(), e);
			throw e;
		}
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable("productId") Long productId)
			throws Exception {
		try {
			Product deletedProduct = productService.deleteProduct(productId);
			logger.info("Deleted product: {}", deletedProduct);
			ProductResponseDto productResponseDto = ProductResponseDto.fromProduct(deletedProduct);
			return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
		} catch (IllegalStateException e) {
			logger.error("Product with id {} not found.", productId);
			throw e;
		} catch (Exception e) {
			logger.error("Error occured while deleting product :{}.", e.getMessage(), e);
			throw e;
		}

	}

	@GetMapping("/search/{keyword}")
	public Iterable<Product> searchProducts(@PathVariable String keyword) {
		return productService.searchProducts(keyword);
	}

}
