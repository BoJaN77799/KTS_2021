package com.app.RestaurantApp.users.appUser;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppUserService {

    public List<AppUser> findAll();

    public AppUser findByEmail(String email);

}
