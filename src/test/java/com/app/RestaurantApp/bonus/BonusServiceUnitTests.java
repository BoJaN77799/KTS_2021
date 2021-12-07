package com.app.RestaurantApp.bonus;

import com.app.RestaurantApp.bonus.dto.BonusDTO;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.employee.Employee;
import com.app.RestaurantApp.users.employee.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BonusServiceUnitTests {

    @Autowired
    private BonusService bonusService;

    @MockBean
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        Employee e = createEmployeeWithBonuses();
        given(employeeService.findEmployeeWithBonuses(Constants.EMAIL)).willReturn(e);
    }

    @Test
    public void testGetBonusesOfEmployee_Valid() throws UserException {
        List<BonusDTO> bonuses = bonusService.getBonusesOfEmployee(Constants.EMAIL);
        assertEquals(3, bonuses.size());

    }

    private Employee createEmployeeWithBonuses() {
        Employee e = new Employee();
        e.setEmail(Constants.EMAIL);
        e.setBonuses(new HashSet<>());

        Bonus b1 = new Bonus(100, 1638831600000L, e); // 1
        Bonus b2 = new Bonus(150, 1638658800000L, e); // 3
        Bonus b3 = new Bonus(200, 1638745200000L, e); // 2

        e.getBonuses().add(b1);
        e.getBonuses().add(b2);
        e.getBonuses().add(b3);

        return e;
    }
}