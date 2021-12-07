package com.app.RestaurantApp.users;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.users.appUser.AppUser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserUtilsUnitTests {
    //todo password test ako treba uopste

    @Test
    public void testCheckUserInfo_NullData(){
        UserException userException = assertThrows(UserException.class, ()->{
            UserUtils.CheckUserInfo(null);
        });
        assertEquals(userException.getMessage(), "Invalid user sent from front!");

        AppUser appUser = new AppUser();
        userException = assertThrows(UserException.class, ()->{
            UserUtils.CheckUserInfo(appUser);
        });
        assertEquals(userException.getMessage(), "Invalid user data sent from front! Null values in attributes!");

        appUser.setFirstName("pera");
        userException = assertThrows(UserException.class, ()->{
            UserUtils.CheckUserInfo(appUser);
        });
        assertEquals(userException.getMessage(), "Invalid user data sent from front! Null values in attributes!");

        appUser.setLastName("djuric");
        userException = assertThrows(UserException.class, ()->{
            UserUtils.CheckUserInfo(appUser);
        });
        assertEquals(userException.getMessage(), "Invalid user data sent from front! Null values in attributes!");

        appUser.setEmail("pera@gmail.com");
        userException = assertThrows(UserException.class, ()->{
            UserUtils.CheckUserInfo(appUser);
        });
        assertEquals(userException.getMessage(), "Invalid user data sent from front! Null values in attributes!");

        appUser.setUserType(UserType.ADMINISTRATOR);
        userException = assertThrows(UserException.class, ()->{
            UserUtils.CheckUserInfo(appUser);
        });
        assertEquals(userException.getMessage(), "Invalid user data sent from front! Null values in attributes!");

        appUser.setAddress("Luzicka");
        userException = assertThrows(UserException.class, ()->{
            UserUtils.CheckUserInfo(appUser);
        });
        assertEquals(userException.getMessage(), "Invalid user data sent from front! Null values in attributes!");

        appUser.setTelephone("80898999");
        userException = assertThrows(UserException.class, ()->{
            UserUtils.CheckUserInfo(appUser);
        });
        assertEquals(userException.getMessage(), "Invalid user data sent from front! Null values in attributes!");
    }

    @Test
    public void testCheckUserInfo(){
        AppUser appUser = new AppUser();
        appUser.setUserType(UserType.ADMINISTRATOR);
        appUser.setFirstName("");
        appUser.setLastName("");
        appUser.setEmail("");
        appUser.setGender("");
        appUser.setAddress("");
        appUser.setTelephone("");

        UserException userException = assertThrows(UserException.class, ()->{
            UserUtils.CheckUserInfo(appUser);
        });
        assertEquals(userException.getMessage(), "First name cannot be blank");
        appUser.setFirstName("Djura");


        userException = assertThrows(UserException.class, ()->{
            UserUtils.CheckUserInfo(appUser);
        });
        assertEquals(userException.getMessage(), "Last name cannot be blank");
        appUser.setLastName("Peric");


        userException = assertThrows(UserException.class, ()->{
            UserUtils.CheckUserInfo(appUser);
        });
        assertEquals(userException.getMessage(), "Address cannot be blank!");
        appUser.setAddress("Luzicka 22");

        appUser.setTelephone("890");
        userException = assertThrows(UserException.class, ()->{
            UserUtils.CheckUserInfo(appUser);
        });
        assertEquals(userException.getMessage(), "Telephone has to be between 5 and 12 digits!");

        appUser.setTelephone("89021231231231123");
        userException = assertThrows(UserException.class, ()->{
            UserUtils.CheckUserInfo(appUser);
        });
        assertEquals(userException.getMessage(), "Telephone has to be between 5 and 12 digits!");

        appUser.setTelephone("89021122d");
        userException = assertThrows(UserException.class, ()->{
            UserUtils.CheckUserInfo(appUser);
        });
        assertEquals(userException.getMessage(), "Invalid phone number!");
        appUser.setTelephone("89021122");


        userException = assertThrows(UserException.class, ()->{
            UserUtils.CheckUserInfo(appUser);
        });
        assertEquals(userException.getMessage(), "Invalid gender!");

        appUser.setGender("dwadwa");

        userException = assertThrows(UserException.class, ()->{
            UserUtils.CheckUserInfo(appUser);
        });
        assertEquals(userException.getMessage(), "Invalid gender!");

        appUser.setGender("MALE");


        userException = assertThrows(UserException.class, ()->{
            UserUtils.CheckUserInfo(appUser);
        });
        assertEquals(userException.getMessage(), "Email address not valid!");

        appUser.setEmail("wddwadwwad");
        userException = assertThrows(UserException.class, ()->{
            UserUtils.CheckUserInfo(appUser);
        });
        assertEquals(userException.getMessage(), "Email address not valid!");

        appUser.setEmail("djura@maildrop.cc");

        assertDoesNotThrow(()->{
            UserUtils.CheckUserInfo(appUser);
        });
    }
}
