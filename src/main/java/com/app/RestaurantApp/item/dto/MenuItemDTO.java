package com.app.RestaurantApp.item.dto;

public class MenuItemDTO {
    private String menuName;
    private String itemId;

    public MenuItemDTO() {}

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
