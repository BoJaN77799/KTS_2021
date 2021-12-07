package com.app.RestaurantApp.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MenuServiceUnitTests {

    @Autowired
    private MenuService menuService;

    @BeforeEach
    public void setup() {

    }
}
