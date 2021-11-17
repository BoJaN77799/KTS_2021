package com.app.RestaurantApp.users.appUser;

import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.dto.UpdateUserDTO;
import com.app.RestaurantApp.users.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;


public interface AppUserService {

    List<AppUser> getAllUsersButAdmin(Long adminID);

    List<AppUser> searchUsersAdmin(String searchField, String userType, Pageable pageable);

    void createUser(AppUser user) throws UserException;

    void updateUser(UpdateUserDTO updateUserDTO) throws UserException;

    void deleteUser(Long id) throws UserException;

    AppUser getUser(Long id);
    
    AppUser findByEmail(String email);

    AppUser getActiveUser(Long id);

    void changePassword(Long id, String oldPassword, String newPassword) throws UserException;
}
