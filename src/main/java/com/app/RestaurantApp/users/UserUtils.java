package com.app.RestaurantApp.users;

import com.app.RestaurantApp.users.appUser.AppUser;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

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

        if (user.getAddress().isBlank()){
            throw new UserException("Address cannot be blank!");
        }

        if (user.getTelephone().length() < 5 || user.getTelephone().length() > 12){
            throw new UserException("Telephone has to be between 5 and 12 digits!");
        }
        if (!user.getTelephone().matches("\\+?\\d+")){
            throw new UserException("Invalid phone number!");
        }

        if (!user.getGender().equals("MALE") && !user.getGender().equals("FEMALE") && !user.getGender().equals("OTHER")){
            throw new UserException("Invalid gender!");
        }

        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";  //pattern za mail

        if (!Pattern.compile(regexPattern).matcher(user.getEmail()).matches()){
            throw new UserException("Email address not valid!");
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
