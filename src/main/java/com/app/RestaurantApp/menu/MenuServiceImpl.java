package com.app.RestaurantApp.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public boolean createUpdateMenu(String name) {
        Menu m = menuRepository.findByName(name);
        if (m == null)
            m = new Menu(name);
        else
            m.setActiveMenu(!m.isActiveMenu());
        menuRepository.save(m);
        return true;
    }
}
