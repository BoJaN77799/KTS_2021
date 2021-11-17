package com.app.RestaurantApp.users.appUser;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.UserUtils;
import com.app.RestaurantApp.users.dto.UpdateUserDTO;
import com.app.RestaurantApp.users.employee.Employee;
import com.app.RestaurantApp.users.employee.EmployeeService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService{

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public AppUser findByEmail(String email) {
        return appUserRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<AppUser> getAllUsersButAdmin(Long adminID) {
        return appUserRepository.findAllUsersButAdmin(adminID);
    }

    @Override
    public List<AppUser> searchUsersAdmin(String searchField, String userType, Pageable pageable){
        if (searchField == null)
            searchField = "";

        if (userType == null){
            userType = "";
        }else if (userType.equalsIgnoreCase("all")){
            userType = "";
        }
        return appUserRepository.searchUsersAdmin(searchField, userType, pageable);
    }

    @Override
    public void createUser(AppUser user) throws UserException{
        UserUtils.CheckUserInfo(user);

        Optional<AppUser> userOptional = appUserRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new UserException("An account with this email already exists");
        }

        String pw = UserUtils.generatePassword(10);

        user.setPassword(pw);
        user.setEmailVerified(false);
        user.setPasswordChanged(false);

        if (user.getUserType() == UserType.BARMAN || user.getUserType() == UserType.COOK ||
                user.getUserType() == UserType.WAITER || user.getUserType() == UserType.HEAD_COOK)
        {
            employeeService.createEmployee(new Employee(user));
        }else{
            appUserRepository.save(user);
        }
    }

    @Override
    public void updateUser(UpdateUserDTO updateUserDTO) throws UserException {
        Optional<AppUser> user = appUserRepository.findByIdAndDeleted(updateUserDTO.getId(), false);
        if (user.isEmpty()) throw new UserException("Invalid user for update!");

        AppUser us = user.get();
        us.setFirstName(updateUserDTO.getFirstName());
        us.setLastName(updateUserDTO.getLastName());
        us.setAddress(updateUserDTO.getAddress());
        us.setTelephone(updateUserDTO.getTelephone());

        UserUtils.CheckUserInfo(us);
        appUserRepository.save(us);
    }

    @Override
    public void deleteUser(Long id) throws UserException{
        Optional<AppUser> user = appUserRepository.findByIdAndDeleted(id, false);
        //todo provera da li je osoba busy trenutno (kuvar, sanker, konobar...)
        if (user.isEmpty()) throw new UserException("Invalid user for deletion!");
        AppUser appUser = user.get();
        appUser.setDeleted(true);
        appUserRepository.save(appUser);
    }

    @Override
    public AppUser getUser(Long id){
        Optional<AppUser> user = appUserRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public AppUser getActiveUser(Long id){
        Optional<AppUser> user = appUserRepository.findByIdAndDeleted(id, false);
        return user.orElse(null);
    }

    @Override
    public void changePassword(Long id, String oldPassword, String newPassword) throws UserException{
        Optional<AppUser> user = appUserRepository.findByIdAndDeleted(id, false);
        if (user.isEmpty()) throw new UserException("Error, user with id not found!");
        if (oldPassword == null || newPassword == null) throw new UserException("Password cannot be null!");

        AppUser appUser = user.get();
        if (!appUser.getPassword().equals(oldPassword)) throw new UserException("Error, old password not correct!");

        if (newPassword.length() < 8) throw new UserException("Error, new password too short!");

        appUser.setPassword(newPassword);
        appUserRepository.save(appUser);
    }
}
