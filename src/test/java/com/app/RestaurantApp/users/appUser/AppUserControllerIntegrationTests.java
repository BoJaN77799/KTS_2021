package com.app.RestaurantApp.users.appUser;


import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.order.dto.OrderDTO;
import com.app.RestaurantApp.order.dto.SimpleOrderDTO;
import com.app.RestaurantApp.security.auth.JwtAuthenticationRequest;
import com.app.RestaurantApp.users.FileUploadUtilTest;
import com.app.RestaurantApp.users.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.app.RestaurantApp.order.Constants.ORDER_CREATED;
import static com.app.RestaurantApp.users.appUser.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppUserControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private MockMvc mockMvc;

    private static HttpHeaders headers;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    static void setup(@Autowired TestRestTemplate testRestTemplate){
        ResponseEntity<UserTokenState> responseEntity =
                testRestTemplate.postForEntity("/api/users/login",
                        new JwtAuthenticationRequest(ADMIN_USERNAME, ADMIN_PASSWORD),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();

        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
    }

    @Test
    public void testFindAllAdmin(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<AppUserAdminUserListDTO[]> responseEntity = restTemplate
                .exchange("/api/users/1", HttpMethod.GET, httpEntity, AppUserAdminUserListDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(6, responseEntity.getBody().length);
        assertThat(responseEntity.getBody()).extracting("userType").doesNotContain(UserType.ADMINISTRATOR);
        assertThat(responseEntity.getBody()).extracting("id")
                .containsExactlyInAnyOrderElementsOf(Arrays.asList(2L, 3L, 4L, 5L, 6L, 7L));
    }

    @Test
    public void testGetUserInfoAdmin(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<UserInfoDTO> responseEntity = restTemplate
                .exchange("/api/users/get_user_info/3", HttpMethod.GET, httpEntity, UserInfoDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        UserInfoDTO userInfoDTO = responseEntity.getBody();

        assertEquals(3, userInfoDTO.getId());
        assertEquals(UserType.WAITER, userInfoDTO.getUserType());
        assertEquals("Luzicka 32, Zajecar", userInfoDTO.getAddress());
        assertEquals("waiter@maildrop.cc", userInfoDTO.getEmail());
        assertEquals("Milorad", userInfoDTO.getFirstName());
        assertEquals("Dodik", userInfoDTO.getLastName());
    }

    @Test
    public void testGetUserInfoAdmin_InvalidUser(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<UserInfoDTO> responseEntity = restTemplate
                .exchange("/api/users/get_user_info/11", HttpMethod.GET, httpEntity, UserInfoDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testGetUserInfoForProfile(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<UserInfoDTO> responseEntity = restTemplate
                .exchange("/api/users/get_user_info_profile/4", HttpMethod.GET, httpEntity, UserInfoDTO.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        UserInfoDTO userInfoDTO = responseEntity.getBody();

        assertEquals(4, userInfoDTO.getId());
        assertEquals(UserType.COOK, userInfoDTO.getUserType());
        assertEquals("Luzicka 32, Gradiska", userInfoDTO.getAddress());
        assertEquals("headcook@maildrop.cc", userInfoDTO.getEmail());
        assertEquals("Igor", userInfoDTO.getFirstName());
        assertEquals("Dodik", userInfoDTO.getLastName());
        assertEquals(40000, userInfoDTO.getSalary());
    }

    @Test
    public void testGetUserInfoForProfile_InactiveUser(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<UserInfoDTO> responseEntity = restTemplate
                .exchange("/api/users/get_user_info_profile/7", HttpMethod.GET, httpEntity, UserInfoDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testGetUserInfoForProfile_InvalidUser(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<UserInfoDTO> responseEntity = restTemplate
                .exchange("/api/users/get_user_info_profile/11", HttpMethod.GET, httpEntity, UserInfoDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test @Transactional
    public void testChangePassword() throws Exception {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("admin", "admininajjaci");
        String jsonChangePasswordDTO = mapper.writeValueAsString(changePasswordDTO);

        mockMvc.perform(put("/api/users/change_password/1")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonChangePasswordDTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Password changed successfully"));
    }

    @Test @Transactional
    public void testChangePassword_InvalidOldPw() throws Exception{
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("admince", "admininajjaci");
        String jsonChangePasswordDTO = mapper.writeValueAsString(changePasswordDTO);

        mockMvc.perform(put("/api/users/change_password/1")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonChangePasswordDTO))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Error, old password not correct!"));

    }

    @Test
    public void testSearchUsersAdmin(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        //1
        ResponseEntity<AppUserAdminUserListDTO[]> responseEntity = restTemplate
                .exchange("/api/users/admin_search?searchField=dod",
                        HttpMethod.GET, httpEntity, AppUserAdminUserListDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(5, responseEntity.getBody().length);
        assertThat(responseEntity.getBody()).extracting("id")
                .containsExactlyInAnyOrderElementsOf(Arrays.asList(3L, 4L, 5L, 6L, 7L));

        //2
        responseEntity = restTemplate.exchange("/api/users/admin_search?userType=MANAGER",
                        HttpMethod.GET, httpEntity, AppUserAdminUserListDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody().length);
        assertThat(responseEntity.getBody()).extracting("userType").containsOnly(UserType.MANAGER);

        //3
        responseEntity = restTemplate.exchange("/api/users/admin_search?searchField=petar&userType=MANAGER",
                HttpMethod.GET, httpEntity, AppUserAdminUserListDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().length);
        assertThat(responseEntity.getBody()).extracting("firstName").containsOnly("Petar");
    }

    @Test @Transactional
    public void testCreateUser() throws Exception{
        MockMultipartFile image = new MockMultipartFile("image", "zuck.jpg",
                MediaType.IMAGE_JPEG_VALUE, FileUploadUtilTest.getTestFileInputStream());

        mockMvc.perform(multipart("/api/users")
                .file(image)
                .headers(headers)
                .param("firstName", "Djura")
                .param("lastName", "Peric")
                .param("email", "djura2@maildrop.cc")
                .param("gender", "MALE")
                .param("telephone", "9089089009")
                .param("address", "Luzicka")
                .param("userType", "ADMINISTRATOR")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("User added successfully"));

        //cleanup
        Optional<AppUser> appUser = appUserRepository.findByEmail("djura2@maildrop.cc");
        if (appUser.isPresent()){
            String pfp = appUser.get().getProfilePhoto();
            Path resultPath = Paths.get(pfp);
            assertTrue(resultPath.toFile().exists() && !resultPath.toFile().isDirectory());
            assertTrue(resultPath.toFile().delete());
            //todo srediti i da izbrise parent folder kasnije
        }
    }

    @Test @Transactional
    public void testUpdateUser() throws Exception{
        UpdateUserDTO updateUserDTO = getUpdateUserDTO();
        String json = mapper.writeValueAsString(updateUserDTO);

        mockMvc.perform(put("/api/users")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("User updated successfully"));
    }

    private CreateUserDTO getCreateUserDTO(){
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setFirstName("Djura");
        createUserDTO.setLastName("Peric");
        createUserDTO.setUserType(UserType.ADMINISTRATOR);
        createUserDTO.setAddress("Luzicka");
        createUserDTO.setEmail(EMAIL_2);
        createUserDTO.setGender("MALE");
        createUserDTO.setTelephone("9089089009");

        return createUserDTO;
    }

    private UpdateUserDTO getUpdateUserDTO(){
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setId(1L);
        updateUserDTO.setFirstName(UPDATE_NAME);
        updateUserDTO.setLastName(UPDATE_LASTNAME);
        updateUserDTO.setAddress("Kisacka");
        updateUserDTO.setTelephone("9089089009");

        return updateUserDTO;
    }
}
