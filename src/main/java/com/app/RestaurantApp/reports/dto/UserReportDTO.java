package com.app.RestaurantApp.reports.dto;

import com.app.RestaurantApp.enums.UserType;

public class UserReportDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private UserType userType;
    private Long ordersAccomplished;


    public UserReportDTO(){}

    public UserReportDTO(Long id, String firstName, String lastName, UserType userType, Long ordersAccomplished) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.ordersAccomplished = ordersAccomplished;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Long getOrdersAccomplished() {
        return ordersAccomplished;
    }

    public void setOrdersAccomplished(Long ordersAccomplished) {
        this.ordersAccomplished = ordersAccomplished;
    }
}
