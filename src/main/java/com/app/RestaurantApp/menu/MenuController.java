package com.app.RestaurantApp.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/menus")
public class  MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping(value = "/createUpdateMenu")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> createUpdateMenu(@RequestBody String name) {
        try {
            if (menuService.createUpdateMenu(name))
                return new ResponseEntity<>("Menu created successfully!", HttpStatus.CREATED);
            else
                return new ResponseEntity<>("Menu updated successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while adding/updating menu!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/findAllWithSpecificStatus/{status}")
    @PreAuthorize("hasRole('MAMAGER')")
    public ResponseEntity<List<Menu>> findAllWithSpecificStatus(@PathVariable boolean status) {
        List<Menu> menus = menuService.findAllWithSpecificStatus(status);
        if (menus.isEmpty())
            return new ResponseEntity<>(menus, HttpStatus.OK);
        return new ResponseEntity<>(menus, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/findAllActiveMenuNames")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<String>> findAllActiveMenuNames() {
        List<String> menuNames = menuService.findAllActiveMenuNames();
        if (menuNames.isEmpty())
            return new ResponseEntity<>(menuNames, HttpStatus.OK);
        return new ResponseEntity<>(menuNames, HttpStatus.NOT_FOUND);
    }

//    @PostMapping(value = "/removeItemFromMenu")
//    @PreAuthorize("hasRole('MANAGER')")
//    public ResponseEntity<String> removeItemFromMenu(@RequestBody MenuItemDTO mi) {
//        try {
//            menuService.removeItemFromMenu(mi);
//            return new ResponseEntity<>("Item is succesfully removed from menu!", HttpStatus.OK);
//        } catch (MenuException | ItemException e ) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Unknown error happened while removing item from menu!", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping(value = "/addItemToMenu")
//    @PreAuthorize("hasRole('MANAGER')")
//    public ResponseEntity<String> addItemToMenu(@RequestBody MenuItemDTO mi) {
//        try {
//            menuService.addItemToMenu(mi);
//            return new ResponseEntity<>("Item is succesfully added to menu!", HttpStatus.OK);
//        } catch (MenuException | ItemException e ) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Unknown error happened while adding item to menu!", HttpStatus.BAD_REQUEST);
//        }
//    }
}
