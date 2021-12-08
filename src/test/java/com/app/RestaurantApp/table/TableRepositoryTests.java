package com.app.RestaurantApp.table;

import com.app.RestaurantApp.users.appUser.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TableRepositoryTests {

    @Autowired
    private TableRepository tableRepository;

    @Test
    public void testFindByFloorAndActive(){

    }
}
