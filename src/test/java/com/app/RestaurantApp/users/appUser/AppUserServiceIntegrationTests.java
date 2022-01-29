package com.app.RestaurantApp.users.appUser;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.users.FileUploadUtilTest;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.dto.CreateUserDTO;
import com.app.RestaurantApp.users.dto.UpdateUserDTO;
import com.app.RestaurantApp.users.employee.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.app.RestaurantApp.users.appUser.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppUserServiceIntegrationTests {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testFindByEmail() {
        assertNotNull(appUserService.findByEmail("admin@maildrop.cc"));
        assertEquals("admin@maildrop.cc", appUserService.findByEmail("admin@maildrop.cc").getEmail());
        assertNull(appUserService.findByEmail("perica@gmail.com"));
    }

    @Test
    public void getAllUsersButAdmin(){
        List<AppUser> users = appUserService.getAllUsersButAdmin("admin@maildrop.cc", null).stream().toList();

        assertEquals(6, users.size());
        assertNotEquals(UserType.ADMINISTRATOR, users.get(0).getUserType());
        assertNotEquals(UserType.ADMINISTRATOR, users.get(1).getUserType());
        assertNotEquals(UserType.ADMINISTRATOR, users.get(2).getUserType());
        assertNotEquals(UserType.ADMINISTRATOR, users.get(3).getUserType());
        assertNotEquals(UserType.ADMINISTRATOR, users.get(4).getUserType());
        assertNotEquals(UserType.ADMINISTRATOR, users.get(5).getUserType());
    }

    @Test
    public void testSearchUsersAdmin() {
        List<AppUser> appUserList =
                appUserService.searchUsersAdmin(null, null, PageRequest.of(0, 10)
                        .withSort(Sort.by("id"))).stream().toList();
        assertEquals(7, appUserList.size());

        appUserList = appUserService.searchUsersAdmin(null, COOK, null).stream().toList();
        assertEquals(2, appUserList.size());
        assertEquals(UserType.COOK, appUserList.get(0).getUserType());
        assertEquals(UserType.COOK, appUserList.get(1).getUserType());

        appUserList = appUserService.searchUsersAdmin(SEARCH_VAL_3, null, null).stream().toList();
        assertEquals(0, appUserList.size());

        appUserList = appUserService.searchUsersAdmin(SEARCH_VAL_1, null,
                PageRequest.of(0,2).withSort(Sort.by("firstName").descending())).stream().toList();
        assertEquals(appUserList.size(), 2);
        assertEquals(2, appUserList.get(0).getId());
        assertEquals(1, appUserList.get(1).getId());
        assertTrue(appUserList.get(0).getLastName().toLowerCase().contains(SEARCH_VAL_1) &&
                appUserList.get(1).getLastName().toLowerCase().contains(SEARCH_VAL_1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ADMINISTRATOR", "WAITER"})
    public void testCreateUser(String userType) {
        CreateUserDTO createUserDTO = getCreateUserDTO(UserType.valueOf(userType));

        int initialSize = appUserRepository.findAll().size();

        try {
            AppUser user = appUserService.createUser(createUserDTO);
            assertNotNull(user.getId());
            assertEquals(EMAIL_2, user.getEmail());
            assertEquals(createUserDTO.getFirstName(), user.getFirstName());

            String pfpPath = "user_profile_photos/" + user.getId() + "/" + TEST_PFP_FILENAME;
            assertEquals(pfpPath, user.getProfilePhoto());

            Path resultPath = Paths.get(pfpPath);
            assertTrue(resultPath.toFile().exists() && !resultPath.toFile().isDirectory());

            int currentSize = appUserRepository.findAll().size();
            assertEquals(initialSize+1, currentSize);

            if (user.getUserType() == UserType.WAITER){
                assertTrue(user instanceof Employee);
            }

            //ciscenje nakon testiranja
            appUserRepository.delete(user);
            assertTrue(resultPath.toFile().delete());
        } catch (UserException e) {
            fail();
        }
    }

    @Test
    @Transactional
    public void testUpdateUser(){
        UpdateUserDTO updateUserDTO = getUpdateUserDTO();

        try {
            AppUser appUser = appUserService.updateUser(updateUserDTO);

            assertEquals(UPDATE_NAME, appUser.getFirstName());
            assertEquals(UPDATE_LASTNAME, appUser.getLastName());

            String pfpPath = "user_profile_photos/" + appUser.getId() + "/" + TEST_PFP_FILENAME;
            assertEquals(pfpPath, appUser.getProfilePhoto());

            Path resultPath = Paths.get(pfpPath);
            assertTrue(resultPath.toFile().exists() && !resultPath.toFile().isDirectory());

        } catch (UserException e) {
            fail();
        }
    }

    @Test
    public void testDeleteUser(){
        try {
            long id = 1L;
            appUserService.deleteUser(id);
            assertNull(appUserService.getActiveUser(id));

            //ponistavanje brisanja
            AppUser appUser = appUserService.getUser(id);
            appUser.setDeleted(false);
            appUserRepository.save(appUser);
        } catch (UserException e) {
            fail();
        }
    }

    @Test
    public void testGetUser(){
        assertNotNull(appUserService.getUser(1L));
        assertNotNull(appUserService.getUser(7L));
        assertNull(appUserService.getUser(8L));
    }

    @Test
    public void testGetActiveUser(){
        assertNotNull(appUserService.getActiveUser(1L));
        assertNull(appUserService.getActiveUser(7L));
        assertNull(appUserService.getActiveUser(8L));
    }

    @Test @Transactional
    public void testChangePassword(){
        long id = 1L;
        String newPw = "adminic22";
        try {
            appUserService.changePassword(id, "admin", newPw);
            AppUser appUser = appUserService.getUser(id);
            assertTrue(passwordEncoder.matches(newPw, appUser.getPassword()));
        } catch (UserException e) {
            fail();
        }

        try {
            appUserService.changePassword(id, "admin", "dwadwaawda");
        } catch (UserException e) {
            assertEquals(e.getMessage(), "Error, old password not correct!");
        }
    }

    @Test
    public void testLoadByUsername(){
        UserDetails appUser = appUserService.loadUserByUsername("admin@maildrop.cc");

        try{
            appUserService.loadUserByUsername("djura@yahoo.com");
            fail();
        }catch (UsernameNotFoundException ignored){ }

        try{
            appUserService.loadUserByUsername("manager123@maildrop.cc");
            fail();
        }catch (UsernameNotFoundException ignored){ }
    }


    private CreateUserDTO getCreateUserDTO(UserType userType){
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setFirstName("Djura");
        createUserDTO.setLastName("Peric");
        createUserDTO.setUserType(userType);
        createUserDTO.setAddress("Luzicka");
        createUserDTO.setEmail(EMAIL_2);
        createUserDTO.setGender("MALE");
        createUserDTO.setTelephone("9089089009");

        MultipartFile image = FileUploadUtilTest.getMultipartTestFile();
        createUserDTO.setImage(image);

        return createUserDTO;
    }

    private UpdateUserDTO getUpdateUserDTO(){
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setId(1L);
        updateUserDTO.setFirstName(UPDATE_NAME);
        updateUserDTO.setLastName(UPDATE_LASTNAME);
        updateUserDTO.setAddress("Kisacka");
        updateUserDTO.setTelephone("9089089009");

        MultipartFile image = FileUploadUtilTest.getMultipartTestFile();
        updateUserDTO.setImage(image);

        return updateUserDTO;
    }
}
