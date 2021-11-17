package com.app.RestaurantApp.users.appUser;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.dto.*;
import com.app.RestaurantApp.users.employee.Employee;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api/users")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;
      
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AppUserAdminUserListDTO> findAllAdmin(@PathVariable(value = "id") Long id) {
        List<AppUser> users = appUserService.getAllUsersButAdmin(id);
        return users.stream().map(AppUserAdminUserListDTO::new).toList();
    }

    @GetMapping(value = "/get_user_info/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoDTO> getUserInfoAdmin(@PathVariable(value = "id") Long id) {
        AppUser user = appUserService.getUser(id);
        if (user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new UserInfoDTO(user), HttpStatus.OK);
    }

    @GetMapping(value = "/get_user_info_profile/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoDTO> getUserInfoForProfile(@PathVariable(value = "id") Long id) {
        AppUser user = appUserService.getActiveUser(id);
        if (user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        if (user.getUserType() == UserType.ADMINISTRATOR || user.getUserType() == UserType.MANAGER){
            return new ResponseEntity<>(new UserInfoDTO(user), HttpStatus.OK);
        }else if (user instanceof Employee){
            return new ResponseEntity<>(new UserInfoDTO((Employee) user), HttpStatus.OK);
        }
        //ako nije instanca employee da se ne slesi
        return new ResponseEntity<>(new UserInfoDTO(user), HttpStatus.OK);
    }

    @PutMapping(value = "/change_password/{id}")
    public ResponseEntity<String> changePassword(@PathVariable("id") Long userId, @RequestBody ChangePasswordDTO changePassword) {
        String oldPassword = changePassword.getOldPassword();
        String newPassword = changePassword.getNewPassword();
        try {
            appUserService.changePassword(userId, oldPassword, newPassword);
            return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown error. Password not changed.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/admin_search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AppUserAdminUserListDTO> searchUsersAdmin(@RequestParam(value = "searchField", required = false) String searchField,
                                                          @RequestParam(value = "userType", required = false) String userType,
                                                          Pageable pageable) {
        List<AppUser> users = appUserService.searchUsersAdmin(searchField, userType, pageable);
        return users.stream().map(AppUserAdminUserListDTO::new).toList();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(@RequestBody CreateUserDTO createUserDTO){
        AppUser appUser = createUserDTO.convertToAppUser();
        try{
            appUserService.createUser(appUser);
            return new ResponseEntity<>("User added successfully", HttpStatus.OK);
        }catch (UserException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while adding user!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserDTO updateUserDTO){
        try{
            appUserService.updateUser(updateUserDTO);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        }catch (UserException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while updating user!", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUser(@PathVariable(value = "id") Long id){
        try{
            appUserService.deleteUser(id);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        }catch (UserException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while deleting user!", HttpStatus.BAD_REQUEST);
        }
    }

}
