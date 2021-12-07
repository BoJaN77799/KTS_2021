package com.app.RestaurantApp.menu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import static com.app.RestaurantApp.menu.Constants.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MenuRepositoryTests {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    public void testFindByNameWithItems() {
        Menu m = menuRepository.findByNameWithItems(VALID_NAME);
        assertEquals(2, m.getItems().size());

        m = menuRepository.findByNameWithItems(INVALID_NAME);
        assertNull(m);

        m = menuRepository.findByNameWithItems(EMPTY_MENU);
        assertEquals(0, m.getItems().size());
    }
}
