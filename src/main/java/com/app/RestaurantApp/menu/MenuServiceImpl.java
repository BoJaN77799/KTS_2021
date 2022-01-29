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
    public void createMenu(String name) throws MenuException {
        Menu m = menuRepository.findByName(name);
        if (m != null) throw new MenuException("Menu already exists!");
        m = new Menu(name);
        menuRepository.save(m);
    }

    @Override
    public void updateMenu(String name) throws MenuException{
        Menu m = menuRepository.findByName(name);
        if (m == null) throw new MenuException("Menu does not exist!");
        m.setActiveMenu(!m.isActiveMenu());
        menuRepository.save(m);
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
