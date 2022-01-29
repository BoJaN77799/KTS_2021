package com.app.RestaurantApp.users.appUser;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.mail.MailService;
import com.app.RestaurantApp.users.FileUploadUtil;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.authority.Authority;
import com.app.RestaurantApp.users.authority.AuthorityService;
import com.app.RestaurantApp.users.dto.CreateUserDTO;
import com.app.RestaurantApp.users.dto.UpdateUserDTO;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static com.app.RestaurantApp.users.appUser.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

@ExtendWith({SpringExtension.class})
@SpringBootTest
public class AppUserServiceUnitTests {

    @Autowired
    private AppUserService appUserService;

    @MockBean
    private AppUserRepository appUserRepositoryMock;

    @MockBean
    private AuthorityService authorityService;

    @MockBean
    private MailService mailServiceMock;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testSearchUsersAdmin() {
        doReturn(new PageImpl<AppUser>(new ArrayList<>()))
                .when(appUserRepositoryMock).searchUsersAdmin("", "", null);

        appUserService.searchUsersAdmin(null, null, null);
        appUserService.searchUsersAdmin(null, "all", null);

        verify(appUserRepositoryMock, times(2)).searchUsersAdmin("", "", null);
    }

    @Test
    public void testCreateUser_EmailInvalid(){
        doReturn(Optional.of(new AppUser())).when(appUserRepositoryMock).findByEmail(EMAIL_1);

        UserException userException = assertThrows(UserException.class, ()->{
           appUserService.createUser(getCreateUserDTO());
        });
        assertEquals(userException.getMessage(), "An account with this email already exists");
    }

    @Test
    public void testCreateUser() throws UserException, IOException, MessagingException {
        CreateUserDTO createUserDTO = getCreateUserDTO();
        MultipartFile image = mock(MultipartFile.class);
        doReturn(PFP_NAME).when(image).getOriginalFilename();
        createUserDTO.setImage(image);

        AppUser appUser = createUserDTO.convertToAppUser();
        appUser.setId(3L);
        doReturn(appUser).when(appUserRepositoryMock).save(any()); //zbog getId kod definisanja naziva profilne
        doReturn(new ArrayList<Authority>()).when(authorityService).findByName("ROLE_ADMIN");

        doReturn(Optional.empty()).when(appUserRepositoryMock).findByEmail(EMAIL_1);
        doNothing().when(mailServiceMock).sendmail(anyString(), anyString(), anyString());

        try (MockedStatic<FileUploadUtil> mocked = mockStatic(FileUploadUtil.class)) {
            mocked.when(() -> FileUploadUtil.saveFile(anyString(), anyString(), any())).thenReturn("");
            appUserService.createUser(createUserDTO);
            verify(appUserRepositoryMock, times(2)).save(any());
            verify(appUserRepositoryMock).findByEmail(EMAIL_1);
            verify(mailServiceMock).sendmail(anyString(), anyString(), anyString());
        }
    }

    @Test
    public void testUpdateUser_InvalidUser(){
        doReturn(Optional.empty()).when(appUserRepositoryMock).findByIdAndDeleted(1L, false);

        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setId(1L);

        UserException userException = assertThrows(UserException.class, ()->{
            appUserService.updateUser(updateUserDTO);
        });
        assertEquals(userException.getMessage(), "Invalid user for update!");
    }

    @Test
    public void testUpdateUser() throws UserException {
        AppUser updatedUser = getAppUserForUpdate();

        doReturn(Optional.of(updatedUser)).when(appUserRepositoryMock).findByIdAndDeleted(1L, false);

        UpdateUserDTO updateUserDTO = getUpdateUserDTO();
        MultipartFile image = mock(MultipartFile.class);
        doReturn(PFP_NAME).when(image).getOriginalFilename();
        updateUserDTO.setImage(image);

        try (MockedStatic<FileUploadUtil> mocked = mockStatic(FileUploadUtil.class)) {
            mocked.when(() -> FileUploadUtil.saveFile(anyString(), anyString(), any())).thenReturn("");
            appUserService.updateUser(updateUserDTO);
            verify(appUserRepositoryMock, times(1)).save(any());
        }
    }

    @Test
    public void testDeleteUser_InvalidUser(){
        doReturn(Optional.empty()).when(appUserRepositoryMock).findByIdAndDeleted(1L, false);

        UserException userException = assertThrows(UserException.class, ()->{
            appUserService.deleteUser(1L);
        });
        assertEquals(userException.getMessage(), "Invalid user for deletion!");
    }

    @Test
    public void testDeleteUser() throws UserException {
        AppUser appUser = new AppUser();
        doReturn(Optional.of(appUser)).when(appUserRepositoryMock).findByIdAndDeleted(1L, false);

        appUserService.deleteUser(1L);

        verify(appUserRepositoryMock).save(appUser);
    }

    @Test
    public void testChangePassword_InvalidUser() {
        doReturn(Optional.empty()).when(appUserRepositoryMock).findByIdAndDeleted(1L, false);

        UserException userException = assertThrows(UserException.class, ()->{
            appUserService.changePassword(1L, "aa", "aaa");
        });
        assertEquals(userException.getMessage(), "Error, user with id not found!");
    }

    @Test
    public void testChangePassword_InvalidNewPassword() {
        doReturn(Optional.of(new AppUser())).when(appUserRepositoryMock).findByIdAndDeleted(1L, false);

        UserException userException = assertThrows(UserException.class, ()->{
            appUserService.changePassword(1L, null, "aaa");
        });
        assertEquals(userException.getMessage(), "Password cannot be null!");

        userException = assertThrows(UserException.class, ()->{
            appUserService.changePassword(1L, "aaaaaaaa", "aaa");
        });
        assertEquals(userException.getMessage(), "Error, new password too short!");
    }

    @Test
    public void testChangePassword_OldPasswordNotCorrect() {
        AppUser appUser = new AppUser();
        appUser.setPassword(passwordEncoder.encode(OLD_PW));
        doReturn(Optional.of(appUser)).when(appUserRepositoryMock).findByIdAndDeleted(1L, false);

        UserException userException = assertThrows(UserException.class, ()->{
            appUserService.changePassword(1L, OLD_PW + "pp", "aaaaaaaa");
        });
        assertEquals(userException.getMessage(), "Error, old password not correct!");
    }

    @Test
    public void testChangePassword() throws UserException {
        AppUser appUser = new AppUser();
        appUser.setPassword(passwordEncoder.encode(OLD_PW));
        doReturn(Optional.of(appUser)).when(appUserRepositoryMock).findByIdAndDeleted(1L, false);

        appUserService.changePassword(1L, OLD_PW, NEW_PW);
        assertTrue(passwordEncoder.matches(NEW_PW, appUser.getPassword()));
        verify(appUserRepositoryMock).save(appUser);
    }

    private AppUser getAppUserForUpdate(){
        AppUser appUser = new AppUser();
        appUser.setId(1L);
        appUser.setFirstName("Djura");
        appUser.setLastName("Peric");
        appUser.setUserType(UserType.BARMAN);
        appUser.setAddress("Luzicka");
        appUser.setEmail("djura@gmail.com");
        appUser.setGender("MALE");
        appUser.setTelephone("9089089009");
        appUser.setProfilePhoto("");

        return appUser;
    }

    private UpdateUserDTO getUpdateUserDTO(){
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setId(1L);
        updateUserDTO.setFirstName("Djura");
        updateUserDTO.setLastName("Peric");
        updateUserDTO.setAddress("Kisacka");
        updateUserDTO.setTelephone("9089089009");

        return updateUserDTO;
    }

    private CreateUserDTO getCreateUserDTO(){
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setFirstName("Djura");
        createUserDTO.setLastName("Peric");
        createUserDTO.setUserType(UserType.ADMINISTRATOR);
        createUserDTO.setAddress("Luzicka");
        createUserDTO.setEmail(Constants.EMAIL_1);
        createUserDTO.setGender("MALE");
        createUserDTO.setTelephone("9089089009");
        createUserDTO.setImage(null);

        return createUserDTO;
    }
}
