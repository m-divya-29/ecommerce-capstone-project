package com.divya29.productservice.dtos;

import com.divya29.productservice.models.Product;
import com.divya29.productservice.models.ProductSpecification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSpecificationDto {
	private Long id;
	private Long productId;
	private String attributeName;
	private String attributeValue;

	public static ProductSpecificationDto fromProductSpecification(ProductSpecification specification) {
		ProductSpecificationDto dto = new ProductSpecificationDto();
		dto.setId(specification.getId());
		dto.setProductId(specification.getProduct() != null ? specification.getProduct().getId() : null);
		dto.setAttributeName(specification.getAttributeName());
		dto.setAttributeValue(specification.getAttributeValue());
		return dto;
	}

	public ProductSpecification toProductSpecification(Product product) {
		ProductSpecification specification = new ProductSpecification();
		specification.setId(this.id);
		specification.setProduct(product);
		specification.setAttributeName(this.attributeName);
		specification.setAttributeValue(this.attributeValue);
		return specification;
	}
}
