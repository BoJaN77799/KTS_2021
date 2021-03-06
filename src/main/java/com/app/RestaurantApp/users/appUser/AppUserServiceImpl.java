package com.app.RestaurantApp.users.appUser;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.mail.MailService;
import com.app.RestaurantApp.users.FileUploadUtil;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.UserUtils;
import com.app.RestaurantApp.users.authority.Authority;
import com.app.RestaurantApp.users.authority.AuthorityService;
import com.app.RestaurantApp.users.dto.CreateUserDTO;
import com.app.RestaurantApp.users.dto.UpdateUserDTO;
import com.app.RestaurantApp.users.employee.Employee;
import com.app.RestaurantApp.users.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Override
    public AppUser findByEmail(String email) {
        return appUserRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Page<AppUser> getAllUsersButAdmin(String adminID, Pageable pageable) {
        return appUserRepository.findAllUsersButAdmin(adminID, pageable);
    }

    @Override
    public Page<AppUser> searchUsersAdmin(String searchField, String userType, Pageable pageable){
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
    public AppUser createUser(CreateUserDTO userDTO) throws UserException{
        AppUser user = userDTO.convertToAppUser();

        UserUtils.CheckUserInfo(user);

        Optional<AppUser> userOptional = appUserRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new UserException("An account with this email already exists");
        }

        String pw = UserUtils.generatePassword(10);

        user.setPassword(passwordEncoder.encode(pw));
        user.setEmailVerified(false);
        user.setPasswordChanged(false);

        String roleName = user.getUserType() == UserType.ADMINISTRATOR ? "ROLE_ADMIN" : "ROLE_" + user.getUserType();

        List<Authority> authorityList = authorityService.findByName(roleName);
        if (user.getUserType() == UserType.HEAD_COOK){
            authorityList.addAll(authorityService.findByName("ROLE_" + UserType.COOK.toString()));
        }

        user.setAuthorities(authorityList);

        AppUser appUser;
        if (user.getUserType() == UserType.BARMAN || user.getUserType() == UserType.COOK ||
                user.getUserType() == UserType.WAITER || user.getUserType() == UserType.HEAD_COOK)
        {
            appUser = employeeRepository.save(new Employee(user));
        }else{
            appUser =  appUserRepository.save(user);
        }

        if (userDTO.getImage() != null){
            try {
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(userDTO.getImage().getOriginalFilename()));
                String uploadDir = "user_profile_photos/" + appUser.getId();

                String path = FileUploadUtil.saveFile(uploadDir, fileName, userDTO.getImage());

                appUser.setProfilePhoto(uploadDir + "/" + fileName);

                appUserRepository.save(appUser);
            }catch (NullPointerException | IOException e){
                System.out.println(e.getMessage());
                user.setProfilePhoto(null);
            }
        }

        try {
            mailService.sendmail("Account creation", MailService.createMessageForUserCreation(user, pw), user.getEmail());
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }

        return appUser;
    }

    @Override
    public AppUser updateUser(UpdateUserDTO updateUserDTO) throws UserException {
        Optional<AppUser> user = appUserRepository.findByIdAndDeleted(updateUserDTO.getId(), false);
        if (user.isEmpty()) throw new UserException("Invalid user for update!");

        AppUser us = user.get();
        us.setFirstName(updateUserDTO.getFirstName());
        us.setLastName(updateUserDTO.getLastName());
        us.setAddress(updateUserDTO.getAddress());
        us.setTelephone(updateUserDTO.getTelephone());

        if (updateUserDTO.getImage() != null){
            try {
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(updateUserDTO.getImage().getOriginalFilename()));
                String uploadDir = "user_profile_photos/" + us.getId();

                String path = FileUploadUtil.saveFile(uploadDir, fileName, updateUserDTO.getImage());

                us.setProfilePhoto(uploadDir + "/" + fileName);
            }catch (NullPointerException | IOException e){
                System.out.println(e.getMessage());
                us.setProfilePhoto(null);
            }
        }

        UserUtils.CheckUserInfo(us);
        return appUserRepository.save(us);
    }

    @Override
    public void deleteUser(Long id) throws UserException{
        Optional<AppUser> user = appUserRepository.findByIdAndDeleted(id, false);
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
        if (newPassword.length() < 8) throw new UserException("Error, new password too short!");

        String newPasswordHash = passwordEncoder.encode(newPassword);
        AppUser appUser = user.get();
        if (!passwordEncoder.matches(oldPassword, appUser.getPassword())) throw new UserException("Error, old password not correct!");

        appUser.setPassword(newPasswordHash);
        appUser.setPasswordChanged(true);
        appUserRepository.save(appUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
        } else if (user.isDeleted()) {
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
        }else{
            return user;
        }
    }
}
