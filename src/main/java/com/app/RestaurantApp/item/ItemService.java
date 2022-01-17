package com.app.RestaurantApp.item;

import com.app.RestaurantApp.item.dto.ItemPriceDTO;
import com.app.RestaurantApp.item.dto.MenuItemDTO;
import com.app.RestaurantApp.menu.MenuException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ItemService {

    List<Item> findAllWithIds(List<Long> ids);

    Item findItemById(Long id);

    boolean createUpdatePriceOnItem(ItemPriceDTO ip) throws ItemException;

    List<ItemPriceDTO> getPricesOfItem(String id) throws ItemException;

    void addItemToMenu(MenuItemDTO menuItemDTO) throws MenuException, ItemException;

    void removeItemFromMenu(MenuItemDTO menuItemDTO) throws MenuException, ItemException;

    Page<Item> findAllItemsWithMenuName(String name, Pageable pageable);
}
