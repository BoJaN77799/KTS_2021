package com.app.RestaurantApp.menu;

import com.app.RestaurantApp.item.ItemException;
import com.app.RestaurantApp.item.dto.ItemDTO;
import com.app.RestaurantApp.menu.dto.MenuItemDTO;

import java.util.List;

public interface MenuService {

    boolean createUpdateMenu(String name) throws MenuException;

    List<ItemDTO> getItemsOfMenu(String name) throws MenuException;

    void removeItemFromMenu(MenuItemDTO mi) throws MenuException, ItemException;

    void addItemToMenu(MenuItemDTO mi) throws MenuException, ItemException;
}
