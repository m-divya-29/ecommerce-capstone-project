package com.divya29.productservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divya29.productservice.dtos.ProductSpecificationDto;
import com.divya29.productservice.services.IProductSpecificationService;

@RestController
@RequestMapping("/specifications")
public class ProductSpecificationController {
	@Autowired
	private IProductSpecificationService specificationService;

	@PostMapping
	public ResponseEntity<ProductSpecificationDto> createSpecification(
			@RequestBody ProductSpecificationDto specificationDto) throws NotFoundException {
		ProductSpecificationDto createdSpecDto = specificationService.createSpecification(specificationDto);
		return ResponseEntity.ok(createdSpecDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductSpecificationDto> updateSpecification(@PathVariable Long id,
			@RequestBody ProductSpecificationDto specificationDto) throws NotFoundException {
		ProductSpecificationDto updatedSpecDto = specificationService.updateSpecification(id, specificationDto);
		return ResponseEntity.ok(updatedSpecDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSpecification(@PathVariable Long id) throws NotFoundException {
		specificationService.deleteSpecification(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductSpecificationDto> getSpecificationById(@PathVariable Long id)
			throws NotFoundException {
		ProductSpecificationDto specificationDto = specificationService.getSpecificationById(id);
		return ResponseEntity.ok(specificationDto);
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<List<ProductSpecificationDto>> getSpecificationsByProductId(@PathVariable Long productId) {
		List<ProductSpecificationDto> specificationsDto = specificationService.getSpecificationsByProductId(productId);
		return ResponseEntity.ok(specificationsDto);
	}

	// Get all specifications
	@GetMapping
	public ResponseEntity<List<ProductSpecificationDto>> getAllSpecifications() {
		List<ProductSpecificationDto> specifications = specificationService.getAllSpecifications();
		return new ResponseEntity<>(specifications, HttpStatus.OK);
	}

	// Search specifications by attribute name and value
	@GetMapping("/{attributeName}/{attributeValue}")
	public ResponseEntity<List<ProductSpecificationDto>> searchSpecificationsByAttributeNameAndValue(
			@PathVariable String attributeName, @PathVariable String attributeValue) throws NotFoundException {
		List<ProductSpecificationDto> specifications = specificationService
				.searchSpecificationsByAttributeNameAndValue(attributeName, attributeValue);
		return new ResponseEntity<>(specifications, HttpStatus.OK);
	}
}