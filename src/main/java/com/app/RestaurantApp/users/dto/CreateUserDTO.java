package com.app.RestaurantApp.users.dto;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.users.appUser.AppUser;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class CreateUserDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String gender;

    private String telephone;

    private String address;

    private UserType userType;

    public CreateUserDTO() {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public AppUser convertToAppUser(){
        AppUser user = new AppUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAddress(address);
        user.setGender(gender);
        user.setTelephone(telephone);
        user.setUserType(userType);
        return user;
    }
}
