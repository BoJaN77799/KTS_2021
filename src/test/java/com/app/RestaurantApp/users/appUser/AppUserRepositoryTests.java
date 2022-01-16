package com.app.RestaurantApp.users.appUser;

import com.app.RestaurantApp.enums.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.app.RestaurantApp.users.appUser.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AppUserRepositoryTests {

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    public void testFindAllUsersButAdmin() {
        List<AppUser> users = appUserRepository.findAllUsersButAdmin("admin@maildrop.cc", null).stream().toList();

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
        List<AppUser> users;
        users = appUserRepository.searchUsersAdmin(SEARCH_VAL_1, EMPTY_OR_ALL_TYPES,
                PageRequest.of(0,2).withSort(Sort.by("firstName").descending())).stream().toList();
        assertEquals(users.size(), 2);
        assertEquals(2, users.get(0).getId());
        assertEquals(1, users.get(1).getId());
        assertTrue(users.get(0).getLastName().toLowerCase().contains(SEARCH_VAL_1) &&
                users.get(1).getLastName().toLowerCase().contains(SEARCH_VAL_1));

        users = appUserRepository.searchUsersAdmin(SEARCH_VAL_2, COOK, null).stream().toList();
        assertEquals(4, users.get(0).getId());
        assertTrue(users.get(0).getFirstName().toLowerCase().contains(SEARCH_VAL_2));

        users = appUserRepository.searchUsersAdmin(EMPTY_SEARCH_FIELD, COOK, null).stream().toList();
        assertEquals(2, users.size());
        assertTrue(users.get(0).getUserType() == UserType.COOK && users.get(1).getUserType() == UserType.COOK);

        users = appUserRepository.searchUsersAdmin(EMPTY_SEARCH_FIELD, EMPTY_OR_ALL_TYPES,
                PageRequest.of(0, 10).withSort(Sort.by("firstName").ascending())).stream().toList();
        assertEquals(users.size(), 7);
        assertEquals(users.get(0).getId(), 6);
        assertEquals(users.get(1).getId(), 1);
        assertEquals(users.get(2).getId(), 4);
        assertEquals(users.get(3).getId(), 5);
        assertEquals(users.get(4).getId(), 3);
        assertEquals(users.get(5).getId(), 2);
        assertEquals(users.get(6).getId(), 7);
    }

    @Test
    public void testFindByEmail(){
        Optional<AppUser> appUserOptional;

        appUserOptional = appUserRepository.findByEmail("admin@maildrop.cc");
        assertTrue(appUserOptional.isPresent());

        appUserOptional = appUserRepository.findByEmail("admincic@gmail.cc");
        assertFalse(appUserOptional.isPresent());

        appUserOptional = appUserRepository.findByEmail("headcook@maildrop.cc");
        assertTrue(appUserOptional.isPresent());

        appUserOptional = appUserRepository.findByEmail("headcook@maildrop.c");
        assertFalse(appUserOptional.isPresent());
    }

    @Test
    public void testFindByIdAndDeleted(){
        Optional<AppUser> appUserOptional;

        appUserOptional = appUserRepository.findByIdAndDeleted(1L, false);
        assertTrue(appUserOptional.isPresent());

        appUserOptional = appUserRepository.findByIdAndDeleted(7L, false);
        assertFalse(appUserOptional.isPresent());

        appUserOptional = appUserRepository.findByIdAndDeleted(7L, true);
        assertTrue(appUserOptional.isPresent());
    }
}
