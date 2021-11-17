package com.app.RestaurantApp.users.dto;

public class ChangePasswordDTO {

    private String oldPassword;
    private String newPassword;

    public ChangePasswordDTO(){

    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
