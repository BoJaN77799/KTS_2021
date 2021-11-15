package com.app.RestaurantApp.menu;

import com.app.RestaurantApp.item.dto.ItemDTO;

import java.util.List;

public interface MenuService {

    boolean createUpdateMenu(String name) throws MenuException;

    List<ItemDTO> getItemsOfMenu(String name) throws MenuException;

}
