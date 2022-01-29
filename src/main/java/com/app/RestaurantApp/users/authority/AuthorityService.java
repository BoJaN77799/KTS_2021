package com.app.RestaurantApp.users.authority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    public Authority findById(Long id) {
        return this.authorityRepository.findById(id).orElse(null);
    }

    public List<Authority> findByName(String name) {
        List<Authority> roles = this.authorityRepository.findByName(name);
        return roles;
    }
}
