package com.app.RestaurantApp.item;

import com.app.RestaurantApp.item.dto.ItemDTO;
import com.app.RestaurantApp.item.dto.ItemPriceDTO;


import java.util.List;

public interface ItemService {

    void insertItem(ItemDTO itemDTO);

    boolean deleteItem(Long id);

    List<Item> findAllWithIds(List<Long> ids);

    Item findItemById(Long id);

    boolean createUpdatePriceOnItem(ItemPriceDTO ip) throws ItemException;

    List<ItemPriceDTO> getPricesOfItem(String id) throws ItemException;

}
