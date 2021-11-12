package com.app.RestaurantApp.users.appUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AppUser> findAll() {
        return appUserService.getAll();
    }
}
