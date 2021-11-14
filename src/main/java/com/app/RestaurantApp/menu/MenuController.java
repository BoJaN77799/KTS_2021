package com.app.RestaurantApp.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping(value = "/createUpdateMenu")
    public boolean createUpdateMenu(@RequestBody String name){
        System.out.println("IME" + name);
        return menuService.createUpdateMenu(name);
    }
}
