package com.divya29.productservice.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

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
    
    private Set<ProductReview> reviews;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private Set<ProductSpecification> specifications;

    private String imageUrl;
}
