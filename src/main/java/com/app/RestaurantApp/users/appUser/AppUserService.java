package com.app.RestaurantApp.users.appUser;

import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.dto.CreateUserDTO;
import com.app.RestaurantApp.users.dto.UpdateUserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.data.domain.Pageable;
import java.util.List;


public interface AppUserService extends UserDetailsService {

    List<AppUser> getAllUsersButAdmin(Long adminID);

    List<AppUser> searchUsersAdmin(String searchField, String userType, Pageable pageable);

    AppUser createUser(CreateUserDTO user) throws UserException;

    AppUser updateUser(UpdateUserDTO updateUserDTO) throws UserException;

    void deleteUser(Long id) throws UserException;

    AppUser getUser(Long id);
    
    AppUser findByEmail(String email);

    AppUser getActiveUser(Long id);

    void changePassword(Long id, String oldPassword, String newPassword) throws UserException;
}
