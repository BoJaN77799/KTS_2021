package com.app.RestaurantApp.item.dto;

import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.item.Item;

public class ItemDTO {

    private Long id;
    private String name;
    private Double cost;
    private String description;
    private String image;
    private CategoryDTO category;
    private boolean deleted;

    public ItemDTO() {
    }

    public ItemDTO(Long id, String name, Double cost, String description, String image, CategoryDTO category, boolean deleted) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.image = image;
        this.category = category;
        this.deleted = deleted;
    }

    public ItemDTO(Item i){
        this.name = i.getName();
        this.cost = i.getCost();
        this.description = i.getDescription();
        this.image = i.getImage();
        this.category = new CategoryDTO(i.getCategory());
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

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
