package com.app.RestaurantApp.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public Menu findByName(String name) {
        return menuRepository.findByName(name);
    }

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
    public List<Menu> findAllWithSpecificStatus(boolean activeMenu) {
        return menuRepository.findAllWithSpecificStatus(activeMenu);
    }

    @Override
    public List<String> findAllActiveMenuNames() {
        List<Menu> menus = menuRepository.findAllWithSpecificStatus(true);
        List<String> names = new ArrayList<>();
        menus.forEach(menu -> names.add(menu.getName()));
        names.add("Nedefinisan");
        return names;
    }
}
