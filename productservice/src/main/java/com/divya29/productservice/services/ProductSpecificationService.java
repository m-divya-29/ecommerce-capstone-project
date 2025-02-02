package com.divya29.productservice.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divya29.productservice.dtos.ProductSpecificationDto;
import com.divya29.productservice.models.Product;
import com.divya29.productservice.models.ProductSpecification;
import com.divya29.productservice.repositories.ProductRepository;
import com.divya29.productservice.repositories.ProductSpecificationRepository;

@Service
public class ProductSpecificationService implements IProductSpecificationService {
	@Autowired
	private ProductSpecificationRepository specificationRepository;

	@Autowired
	private ProductRepository productRepository;

	// Get all specifications
	@Override
	public List<ProductSpecificationDto> getAllSpecifications() {
		return specificationRepository.findAll().stream().map(ProductSpecificationDto::fromProductSpecification)
				.collect(Collectors.toList());
	}

	// Search specifications by attribute name and value
	@Override
	public List<ProductSpecificationDto> searchSpecificationsByAttributeNameAndValue(String attributeName,
			String attributeValue) throws IllegalStateException {
		List<ProductSpecification> specifications = specificationRepository
				.findByAttributeNameAndAttributeValue(attributeName, attributeValue);
		if (specifications.isEmpty()) {
			throw new IllegalStateException("No specifications found for the given attribute name and value.");
		}
		return specifications.stream().map(ProductSpecificationDto::fromProductSpecification)
				.collect(Collectors.toList());
	}

	@Override
	public ProductSpecificationDto createSpecification(ProductSpecificationDto specificationDto)
			throws IllegalStateException {
		Product product = productRepository.findById(specificationDto.getProductId()).orElseThrow(
				() -> new IllegalStateException("Product not found with id " + specificationDto.getProductId()));

		ProductSpecification specification = specificationDto.toProductSpecification(product);
		ProductSpecification savedSpec = specificationRepository.save(specification);
		return ProductSpecificationDto.fromProductSpecification(savedSpec);
	}

	@Override
	public ProductSpecificationDto updateSpecification(Long id, ProductSpecificationDto updatedSpecDto)
			throws IllegalStateException {
		ProductSpecification existingSpec = specificationRepository.findById(id)
				.orElseThrow(() -> new IllegalStateException("Specification not found with id " + id));

		existingSpec.setAttributeName(updatedSpecDto.getAttributeName());
		existingSpec.setAttributeValue(updatedSpecDto.getAttributeValue());

		ProductSpecification updatedSpec = specificationRepository.save(existingSpec);
		return ProductSpecificationDto.fromProductSpecification(updatedSpec);
	}

	@Override
	public void deleteSpecification(Long id) throws IllegalStateException {
		if (!specificationRepository.existsById(id)) {
			throw new IllegalStateException("Specification not found with id " + id);
		}
		specificationRepository.deleteById(id);
	}

	@Override
	public ProductSpecificationDto getSpecificationById(Long id) throws IllegalStateException {
		ProductSpecification specification = specificationRepository.findById(id)
				.orElseThrow(() -> new IllegalStateException("Specification not found with id " + id));
		return ProductSpecificationDto.fromProductSpecification(specification);
	}

	@Override
	public List<ProductSpecificationDto> getSpecificationsByProductId(Long productId) {
		List<ProductSpecification> specifications = specificationRepository.findByProductId(productId);
		return specifications.stream().map(ProductSpecificationDto::fromProductSpecification)
				.collect(Collectors.toList());
	}
}