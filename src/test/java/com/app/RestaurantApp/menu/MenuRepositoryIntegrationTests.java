package com.app.RestaurantApp.menu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static com.app.RestaurantApp.menu.Constants.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MenuRepositoryIntegrationTests {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    public void testFindAllWithSpecificStatus() {
        String[] activeMenuNames = new String[]{FIRST_ACTIVE_MENU, SECOND_ACTIVE_MENU};
        String[] inactiveMenuNames = new String[]{FIRST_INACTIVE_MENU, SECOND_INACTIVE_MENU};

        List<Menu> menus = menuRepository.findAllWithSpecificStatus(true);
        assertEquals(2, menus.size());
        assertTrue(checkIfListContainName(activeMenuNames, menus));

        menus = menuRepository.findAllWithSpecificStatus(false);
        assertEquals(2, menus.size());
        assertTrue(checkIfListContainName(inactiveMenuNames, menus));
    }

    private boolean checkIfListContainName(String[] checkList, List<Menu> menus) {
        boolean indicator = true;
        for (Menu m : menus) {
            if (!Arrays.stream(checkList).anyMatch(m.getName()::equals)) {
                indicator = false;
                break;
            }
        }
        return indicator;
    }
}
