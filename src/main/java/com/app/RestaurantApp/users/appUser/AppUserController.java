package com.app.RestaurantApp.users.appUser;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.mail.MailService;
import com.app.RestaurantApp.security.TokenUtils;
import com.app.RestaurantApp.security.auth.JwtAuthenticationRequest;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.dto.*;
import com.app.RestaurantApp.users.employee.Employee;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class AppUserController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private MailService mailService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AppUserAdminUserListDTO>> findAllAdmin(@PathVariable(value = "id") Long id) {
        //todo dodati pageable ovde
        List<AppUser> users = appUserService.getAllUsersButAdmin(id);
        return new ResponseEntity<>(users.stream().map(AppUserAdminUserListDTO::new).toList(), HttpStatus.OK);
    }

    @GetMapping(value = "/get_user_info/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public List<AppUserAdminUserListDTO> searchUsersAdmin(@RequestParam(value = "searchField", required = false) String searchField,
                                                          @RequestParam(value = "userType", required = false) String userType,
                                                          Pageable pageable) {
        List<AppUser> users = appUserService.searchUsersAdmin(searchField, userType, pageable);
        return users.stream().map(AppUserAdminUserListDTO::new).toList();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createUser(@ModelAttribute CreateUserDTO createUserDTO){
        try{
            appUserService.createUser(createUserDTO);
        }catch (UserException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while adding user!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User added successfully", HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserDTO updateUserDTO){
        //todo da moze da update profilnu
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
    @PreAuthorize("hasRole('ADMIN')")
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

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                                    HttpServletResponse response) throws IOException, MessagingException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));

        // Ubaci korisnika u trenutni security kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        AppUser user = (AppUser) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getEmail(), user.getUserType().toString(), user.getId());
        int expiresIn = tokenUtils.getExpiredIn();

        //mailService.sendmail("Login", "Login success.", user.getEmail());

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

}
