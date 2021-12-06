package com.app.RestaurantApp.item;

public class ItemUtils {

    public static void CheckItemInfo(Item item) throws ItemException {
        if (item == null) {
            throw new ItemException("Invalid item sent from front!");
        } else if (item.getName() == null || item.getDescription() == null
                || item.getImage() == null || item.getCategory() == null || item.getItemType() == null) {
            throw new ItemException("Invalid item data sent from front! Null values in attributes!");
        }

        if (item.getName().isBlank()) {
            throw new ItemException("Name cannot be blank!");
        }

        if (item.getCost() <= 0) {
            throw new ItemException("Cost cannot be less or equal zero!");
        }

        if (item.getDescription().isBlank()) {
            throw new ItemException("Description cannot be blank!");
        }

        if (item.getDescription().length() > 256) {
            throw new ItemException("Description cannot be greater than 256 characters!");
        }

        if (item.getImage().isBlank()) {
            throw new ItemException("Image path cannot be blank!");
        }

        if (item.getCategory().getName().isBlank()) {
            throw new ItemException("Category name cannot be blank!");
        }
    }
}
