package com.app.RestaurantApp.users.dto;

import com.app.RestaurantApp.users.employee.Employee;

public class NewEmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String telephone;
    private String adress;
    private String userType;
    private String profilePhoto;
    private double salary;

    public NewEmployeeDTO() {}

    public NewEmployeeDTO(Employee e) {
        this.id = e.getId();
        this.firstName = e.getFirstName();
        this.lastName = e.getLastName();
        this.email = e.getEmail();
        this.gender = e.getGender().toString();
        this.telephone = e.getTelephone();
        this.adress = e.getAddress();
        this.userType = e.getUserType().toString();
        this.profilePhoto = e.getProfilePhoto();
        this.salary = e.getSalary();
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
