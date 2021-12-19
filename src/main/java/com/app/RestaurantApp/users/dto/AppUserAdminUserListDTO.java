package com.app.RestaurantApp.users.dto;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.users.appUser.AppUser;

public class AppUserAdminUserListDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private UserType userType;
    private boolean isPasswordChanged;

    public AppUserAdminUserListDTO() {
    }

    public AppUserAdminUserListDTO(AppUser user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.telephone = user.getTelephone();
        this.userType = user.getUserType();
        this.isPasswordChanged = user.isPasswordChanged();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isPasswordChanged() {
        return isPasswordChanged;
    }

    public void setPasswordChanged(boolean passwordChanged) {
        isPasswordChanged = passwordChanged;
    }
}
