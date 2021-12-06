//package com.app.RestaurantApp.users.appUser;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Optional;
//
//import static org.mockito.BDDMockito.given;
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class AppUserServiceUnitTests {
//
//    @Autowired
//    private AppUserService appUserService;
//
//    @MockBean
//    private AppUserRepository appUserRepositoryMock;
//
//    @Test
//    public void testFindByEmail() {
//        given(appUserRepositoryMock.findByEmail("")).willReturn(Optional.empty());
//        assertNull(appUserService.findByEmail(""));
//    }
//}
