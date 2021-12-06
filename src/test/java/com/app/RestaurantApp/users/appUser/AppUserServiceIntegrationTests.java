//package com.app.RestaurantApp.users.appUser;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class AppUserServiceIntegrationTests {
//
//    @Autowired
//    private AppUserService appUserService;
//
//    @Test
//    public void testFindByEmail() {
//        AppUser appUser = appUserService.findByEmail("admin@maildrop.cc");
//        assertEquals("ADMINISTRATOR", appUser.getUserType().toString());
//        assertEquals("Filip", appUser.getFirstName());
//        assertNotEquals("Milorad", appUser.getFirstName());
//        assertEquals("Markovic", appUser.getLastName());
//    }
//}
