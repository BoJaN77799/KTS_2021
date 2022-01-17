package com.app.RestaurantApp.menu;

import java.util.List;

public interface MenuService {

    Menu findByName(String name);

    boolean createUpdateMenu(String name) throws MenuException;

    List<Menu> findAllWithSpecificStatus(boolean activeMenu);

    List<String> findAllActiveMenuNames();
}
