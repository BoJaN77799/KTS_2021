package com.app.RestaurantApp.menu;

import com.sun.mail.iap.Response;
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

    @PostMapping(value = "/createMenu")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> createMenu(@RequestBody String name) {
        try {
            menuService.createMenu(name);
            return new ResponseEntity<>("Menu created successfully!", HttpStatus.OK);
        } catch (MenuException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while creating menu!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/updateMenu")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> updateMenu(@RequestBody String name)  {
        try {
            menuService.updateMenu(name);
            return new ResponseEntity<>("Menu updated successfully!", HttpStatus.OK);
        } catch (MenuException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while updating menu!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/findAllWithSpecificStatus/{status}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<Menu>> findAllWithSpecificStatus(@PathVariable boolean status) {
        try {
            List<Menu> menus = menuService.findAllWithSpecificStatus(status);
            if (menus.isEmpty())
                return new ResponseEntity<>(menus, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(menus, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/findAllActiveMenuNames")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<String>> findAllActiveMenuNames() {
        try {
            List<String> menuNames = menuService.findAllActiveMenuNames();
            if (menuNames.isEmpty())
                return new ResponseEntity<>(menuNames, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(menuNames, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
