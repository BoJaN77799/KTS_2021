package com.app.RestaurantApp.category;

import com.app.RestaurantApp.category.dto.CategoryDTO;

import javax.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Category(){}

    public Category(Long id, String name){}

    public Category(CategoryDTO category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public Category(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
