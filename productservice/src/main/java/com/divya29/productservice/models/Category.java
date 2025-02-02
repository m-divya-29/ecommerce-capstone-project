package com.divya29.productservice.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Category extends BaseModel  {
    private String name;
    private String description;

    @JsonManagedReference
    @OneToMany(mappedBy = "category")
    private List<Product> products  = new ArrayList<>();
}
