package com.app.RestaurantApp.item;

import com.app.RestaurantApp.item.dto.ItemDTO;


import java.util.List;

public interface ItemService {

    void insertItem(ItemDTO itemDTO);

    boolean deleteItem(Long id);

    List<Item> findAllWithIds(List<Long> ids);

}
