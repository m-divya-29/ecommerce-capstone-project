package com.divya29.productservice.dtos;

import com.divya29.productservice.models.Category;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryRequestDto {
    private String name;
    private String description;

    public Category toCategory(){

        Category category = new Category();
        category.setName(this.getName());
        category.setDescription(this.getDescription());
        return category;

    }

    public static CategoryRequestDto fromCategory(Category category) {

        CategoryRequestDto categoryDto = new CategoryRequestDto();
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());

        return categoryDto;
    }
}
