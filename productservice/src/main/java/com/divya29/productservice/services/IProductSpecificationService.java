package com.divya29.productservice.services;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.divya29.productservice.dtos.ProductSpecificationDto;

public interface IProductSpecificationService {
	List<ProductSpecificationDto> getAllSpecifications();

	List<ProductSpecificationDto> searchSpecificationsByAttributeNameAndValue(String attributeName,
			String attributeValue) throws NotFoundException;

	ProductSpecificationDto createSpecification(ProductSpecificationDto specificationDto) throws NotFoundException;

	ProductSpecificationDto updateSpecification(Long id, ProductSpecificationDto updatedSpecDto)
			throws NotFoundException;

	void deleteSpecification(Long id) throws NotFoundException;

	ProductSpecificationDto getSpecificationById(Long id) throws NotFoundException;

	List<ProductSpecificationDto> getSpecificationsByProductId(Long productId);
}
