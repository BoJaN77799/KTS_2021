package com.app.RestaurantApp.item.dto;

import com.app.RestaurantApp.enums.ItemType;
import com.app.RestaurantApp.item.Item;

public class ItemDTOCatString {

    private Long id;
    private String name;
    private Double cost;
    private String description;
    private String image;
    private String category;
    private ItemType itemType;
    private boolean deleted;

    public ItemDTOCatString() {
    }

    public ItemDTOCatString(Long id, String name, Double cost, String description, String image, String category, ItemType itemType, boolean deleted) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.image = image;
        this.category = category;
        this.itemType = itemType;
        this.deleted = deleted;
    }

    public ItemDTOCatString(Item i){
        this.id = i.getId();
        this.name = i.getName();
        this.cost = i.getCost();
        this.description = i.getDescription();
        this.image = i.getImage();
        this.category = String.valueOf(i.getCategory());
        this.itemType = i.getItemType();
        this.deleted = i.isDeleted();
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
