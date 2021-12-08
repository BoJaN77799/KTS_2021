package com.app.RestaurantApp.users.appUser;


import com.app.RestaurantApp.users.dto.AppUserAdminUserListDTO;
import com.app.RestaurantApp.users.dto.UserInfoDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppUserRESTIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

//    @Test
//    public void testFindAllAdmin(){
//        ResponseEntity<AppUserAdminUserListDTO[]> responseEntity = restTemplate
//                .getForEntity("/api/users/1", AppUserAdminUserListDTO[].class);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
////        AppUserAdminUserListDTO[] userListDTOS = responseEntity.getBody();
////
////        assertNotNull(userListDTOS);
//        //assertEquals(6, userListDTOS.length);
//    }
//
//    @Test
//    @WithMockUser(authorities = {"ADMINISTRATOR"})
//    public void testGetUserInfo(){
//        ResponseEntity<UserInfoDTO> responseEntity = restTemplate
//                .getForEntity("/api/users/get_user_info/1", UserInfoDTO.class);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }
}
