package com.app.RestaurantApp.item.dto;

import com.app.RestaurantApp.category.dto.CategoryDTO;

public class ItemDTO {

    private Long id;
    private String name;
    private Double cost;
    private String description;
    private String image;
    private CategoryDTO categoryDTO;

    public ItemDTO(){}

    public ItemDTO(Long id, String name, Double cost, String description, String image, CategoryDTO categoryDTO) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.image = image;
        this.categoryDTO = categoryDTO;
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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }
}

