package com.app.RestaurantApp.menu;

import com.app.RestaurantApp.item.Item;
import com.app.RestaurantApp.item.dto.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public boolean createUpdateMenu(String name) {
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
    public List<ItemDTO> getItemsOfMenu(String name) {
        Menu m = menuRepository.findByName(name);

        List<ItemDTO> items = new ArrayList<>();
        for (Item i : m.getItems())
            items.add(new ItemDTO(i));
        return items;
    }
}
