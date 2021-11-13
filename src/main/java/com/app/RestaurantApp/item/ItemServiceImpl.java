package com.app.RestaurantApp.item;

import com.app.RestaurantApp.item.dto.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void insertItem(ItemDTO itemDTO) {
        Item item = new Item(itemDTO);
        itemRepository.save(item);
    }

    @Override
    public boolean deleteItem(Long id) {
        return false;
    }

}
