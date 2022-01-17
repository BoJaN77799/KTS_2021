package com.app.RestaurantApp.item.dto;

import com.app.RestaurantApp.item.Item;

public class ItemMenuDTO {
    private long id;
    private String name;
    private  String description;
    private String image;
    private double cost;
    private double currentPrice;
    private String itemType;
    private String menu;

    public ItemMenuDTO() {}

    public ItemMenuDTO(Item i) {
        this.id = i.getId();
        this.name = i.getName();
        this.description = i.getDescription();
        this.image = i.getImage();
        this.cost = i.getCost();
        this.currentPrice = i.getCurrentPrice();
        this.itemType = i.getItemType().toString();
        this.menu = i.getMenu();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }
}
