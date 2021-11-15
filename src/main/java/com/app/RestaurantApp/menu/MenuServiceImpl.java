package com.app.RestaurantApp.menu;

import com.app.RestaurantApp.item.Item;
import com.app.RestaurantApp.item.ItemException;
import com.app.RestaurantApp.item.ItemService;
import com.app.RestaurantApp.item.dto.ItemDTO;
import com.app.RestaurantApp.menu.dto.MenuItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ItemService itemService;

    @Override
    public boolean createUpdateMenu(String name){
        Menu m = menuRepository.findByName(name);
        boolean indicator = false;
        if (m == null) {
            m = new Menu(name);
            indicator = true;
        }
        else
            m.setActiveMenu(!m.isActiveMenu());
        menuRepository.save(m);
        return indicator;
    }

    @Override
    @Transactional
    public List<ItemDTO> getItemsOfMenu(String name) throws MenuException {
        Menu m = menuRepository.findByName(name);
        if (m == null) throw new MenuException("Menu does not exist!");

        List<ItemDTO> items = new ArrayList<>();
        for (Item i : m.getItems())
            items.add(new ItemDTO(i));
        return items;
    }

    @Override
    @Transactional
    public void removeItemFromMenu(MenuItemDTO mi) throws MenuException, ItemException {
        Menu m = menuRepository.findByName(mi.getMenuName());
        if (m == null) throw new MenuException("Menu does not exist!");

        Item i = itemService.findItemById(Long.valueOf(mi.getItemId()));
        if (i == null) throw new ItemException("Item does not exist!");

        m.getItems().remove(i);
        menuRepository.save(m);
    }
}
