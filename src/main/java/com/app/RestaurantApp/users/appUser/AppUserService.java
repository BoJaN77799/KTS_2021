package com.app.RestaurantApp.users.appUser;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppUserService {

    List<AppUser> findAll();

    AppUser findByEmail(String email);

}
