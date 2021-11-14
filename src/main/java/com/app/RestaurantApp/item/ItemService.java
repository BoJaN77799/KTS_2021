package com.app.RestaurantApp.item;

import com.app.RestaurantApp.item.dto.ItemDTO;

public interface ItemService {

    void insertItem(ItemDTO itemDTO);

    boolean deleteItem(Long id);

}
