package com.divya29.productservice.models;

import java.util.Set;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(indexName = "products")
@Entity
public class Product extends BaseModel {
	private String title;
	private String description;
	private Double price;
	private Integer stockQuantity;
	@ManyToOne
	@JsonBackReference

	private Category category;

	@OneToMany(mappedBy = "product")
	@JsonManagedReference
	@Field(type = FieldType.Nested, includeInParent = true)
	private Set<ProductReview> reviews;

	@OneToMany(mappedBy = "product")
	@JsonManagedReference
	@Field(type = FieldType.Nested, includeInParent = true)
	private Set<ProductSpecification> specifications;

	@Field(type = FieldType.Nested, includeInParent = true)
	private String imageUrl;
}
