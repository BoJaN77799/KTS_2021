package com.app.RestaurantApp.menu;

import java.util.List;

public interface MenuService {

    Menu findByName(String name);

    void createMenu(String name) throws MenuException;

    void updateMenu(String name) throws MenuException;

    List<Menu> findAllWithSpecificStatus(boolean activeMenu);

    List<String> findAllActiveMenuNames();
}
