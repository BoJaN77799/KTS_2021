package com.app.RestaurantApp.users.appUser;

import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.dto.AppUserAdminUserListDTO;
import com.app.RestaurantApp.users.dto.CreateUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("api/users")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AppUserAdminUserListDTO> findAll(@PathVariable(value = "id") Long id) {
        List<AppUser> users = appUserService.getAllUsersButAdmin(id);
        return users.stream().map(AppUserAdminUserListDTO::new).toList();
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
}
