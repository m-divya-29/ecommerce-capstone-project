package com.divya29.productservice.clients.fakestoreapi;

import java.io.Serializable;

import com.divya29.productservice.models.Category;
import com.divya29.productservice.models.Product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FakeStoreProductDto implements Serializable {
    private static final long serialVersionUID = 1L;
	private Long id;
    private String title;
    private Double price;
    private String description;
    private String image;
    private String category;

    public Product toProduct(){
        Product product = new Product();
        product.setId(this.getId());
        product.setTitle(this.getTitle());
        product.setPrice(this.getPrice());
        Category category = new Category();
        category.setName(this.getCategory());
        product.setCategory(category);
        product.setImageUrl(this.getImage());
        return product;
    }

    public static FakeStoreProductDto fromProduct(Product product) {
        FakeStoreProductDto productDto = new FakeStoreProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setImage(product.getImageUrl());
        productDto.setCategory(product.getCategory().getName());
        return productDto;
    }

}
