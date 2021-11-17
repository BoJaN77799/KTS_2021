package com.app.RestaurantApp.users.dto;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.users.appUser.AppUser;
import com.app.RestaurantApp.users.employee.Employee;

public class UserInfoDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String gender;

    private String telephone;

    private String address;

    private UserType userType;

    private boolean isPasswordChanged;

    private double salary;

    public UserInfoDTO(){ }

    public UserInfoDTO(AppUser appUser){
        this.id = appUser.getId();
        this.firstName = appUser.getFirstName();
        this.lastName = appUser.getLastName();
        this.email = appUser.getEmail();
        this.gender = appUser.getGender();
        this.telephone = appUser.getTelephone();
        this.address = appUser.getAddress();
        this.userType = appUser.getUserType();
        this.isPasswordChanged = appUser.isPasswordChanged();
    }

    public UserInfoDTO(Employee appUser){
        this.id = appUser.getId();
        this.firstName = appUser.getFirstName();
        this.lastName = appUser.getLastName();
        this.email = appUser.getEmail();
        this.gender = appUser.getGender();
        this.telephone = appUser.getTelephone();
        this.address = appUser.getAddress();
        this.userType = appUser.getUserType();
        this.isPasswordChanged = appUser.isPasswordChanged();
        this.salary = appUser.getSalary();
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

    public boolean isPasswordChanged() {
        return isPasswordChanged;
    }

    public void setPasswordChanged(boolean passwordChanged) {
        isPasswordChanged = passwordChanged;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
