package com.app.RestaurantApp.menu;

import com.app.RestaurantApp.item.ItemException;
import com.app.RestaurantApp.item.dto.ItemDTO;
import com.app.RestaurantApp.menu.dto.MenuItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping(value = "/createUpdateMenu")
    public ResponseEntity<String> createUpdateMenu(@RequestBody String name) throws MenuException {
        if (menuService.createUpdateMenu(name))
            return new ResponseEntity<>("Menu created successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Menu updated successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/getItemsOfMenu/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ItemDTO> getItemsOfMenu(@PathVariable String name) throws MenuException {
        return menuService.getItemsOfMenu(name);
    }

    @PostMapping(value = "/removeItemFromMenu")
    public ResponseEntity<String> removeItemFromMenu(@RequestBody MenuItemDTO mi) throws MenuException, ItemException {
        menuService.removeItemFromMenu(mi);
        return new ResponseEntity<>("Item is succesfully removed from menu!", HttpStatus.OK);
    }

    @PostMapping(value = "/addItemToMenu")
    public ResponseEntity<String> addItemToMenu(@RequestBody MenuItemDTO mi) throws MenuException, ItemException {
        menuService.addItemToMenu(mi);
        return new ResponseEntity<>("Item is succesfully added to menu!", HttpStatus.OK);
    }
}
