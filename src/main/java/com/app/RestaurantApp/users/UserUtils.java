package com.app.RestaurantApp.users;

import com.app.RestaurantApp.users.appUser.AppUser;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class UserUtils {

    public static void CheckUserInfo(AppUser user) throws UserException {
        if (user == null) {
            throw new UserException("Invalid user sent from front!");
        }else if (user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null
                || user.getUserType() == null || user.getAddress() == null || user.getTelephone() == null
                || user.getGender() == null){
            throw new UserException("Invalid user data sent from front! Null values in attributes!");
        }

        if (user.getFirstName().isBlank()) {
            throw new UserException("First name cannot be blank");
        }

        if (user.getLastName().isBlank()) {
            throw new UserException("Last name cannot be blank");
        }

        if (user.getEmail().isBlank()){
            throw new UserException("Email cannot be blank!");
        }

        if (user.getAddress().isBlank()){
            throw new UserException("Address cannot be blank!");
        }

        if (user.getTelephone().isBlank()){
            throw new UserException("Telephone number cannot be blank!");
        }

        if (user.getGender().isBlank()){
            throw new UserException("Gender cannot be blank!");
        }
    }

    public static String generatePassword(int pwLength) {
        Random rand = new Random();
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder pw = new StringBuilder(pwLength);

        for(int i = 0; i < pwLength; i++) {
                pw.append(str.charAt(rand.nextInt(str.length())));
        }

        return pw.toString();
    }
}
