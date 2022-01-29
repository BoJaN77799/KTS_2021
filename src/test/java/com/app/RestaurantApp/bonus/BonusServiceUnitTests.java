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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import static com.app.RestaurantApp.bonus.Constants.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BonusServiceUnitTests {

    @Autowired
    private BonusService bonusService;

    @MockBean
    private EmployeeService employeeServiceMock;

    @MockBean
    private BonusRepository bonusRepositoryMock;

    @BeforeEach
    public void setup() {
        Employee e = createEmployeeWithBonuses();

        given(employeeServiceMock.findEmployeeWithBonuses(EMAIL)).willReturn(e);
        given(employeeServiceMock.findByEmail(EMAIL)).willReturn(e);
    }

    @Test
    public void testGetBonusesOfEmployee_Valid() throws UserException {
        List<BonusDTO> bonuses = bonusService.getBonusesOfEmployee(EMAIL);

        verify(employeeServiceMock, times(1)).findEmployeeWithBonuses(EMAIL);

        assertEquals(3, bonuses.size());

        //sorted by dates
        assertEquals(150.0, bonuses.get(0).getAmount());
        assertEquals(200.0, bonuses.get(1).getAmount());
        assertEquals(100.0, bonuses.get(2).getAmount());

        assertEquals("05.12.2021.", bonuses.get(0).getDate());
        assertEquals("06.12.2021.", bonuses.get(1).getDate());
        assertEquals("07.12.2021.", bonuses.get(2).getDate());
    }

    @Test
    public void testGetBonusesOfEmployee_Invalid() {
        given(employeeServiceMock.findEmployeeWithBonuses(EMAIL)).willReturn(null);

        Exception exception = assertThrows(UserException.class, () -> bonusService.getBonusesOfEmployee(EMAIL));

        assertEquals(INVALID_USER_MESSAGE, exception.getMessage());
    }

    @Test
    public void testCreateBonus_Valid() throws BonusException, UserException {
        BonusDTO bonusDTO = createBonusDTO();
        Bonus bonusMocked = new Bonus(bonusDTO);
        Employee e = employeeServiceMock.findEmployeeWithBonuses(bonusDTO.getEmail());
        bonusMocked.setEmployee(e);
        given(bonusRepositoryMock.save(any())).willReturn(bonusMocked);

        bonusService.createBonus(bonusDTO);

        verify(employeeServiceMock, times(2)).findEmployeeWithBonuses(EMAIL);
        verify(bonusRepositoryMock, times(1)).save(any());

    }

    @Test
    public void testCreateBonus_InvalidUser() {
        given(employeeServiceMock.findEmployeeWithBonuses(EMAIL)).willReturn(null);

        Exception exception = assertThrows(UserException.class, () ->
        {
            BonusDTO bonusDTO = createBonusDTO();
            bonusService.createBonus(bonusDTO);
        });

        assertEquals(INVALID_USER_MESSAGE, exception.getMessage());
    }

    @Test
    public void testCreateBonus_InvalidAmount() {
        Exception exception = assertThrows(BonusException.class, () ->
        {
            BonusDTO bonusDTO = createBonusDTO();
            bonusDTO.setAmount(-1);
            bonusService.createBonus(bonusDTO);
        });

        assertEquals(INVALID_AMOUNT, exception.getMessage());
    }

    private Employee createEmployeeWithBonuses() {
        Employee e = new Employee();
        e.setEmail(EMAIL);
        e.setBonuses(new HashSet<>());

        Bonus b1 = new Bonus(100, 1638831600000L, e); // 1
        Bonus b2 = new Bonus(150, 1638658800000L, e); // 3
        Bonus b3 = new Bonus(200, 1638745200000L, e); // 2

        e.getBonuses().add(b1);
        e.getBonuses().add(b2);
        e.getBonuses().add(b3);

        return e;
    }

    private BonusDTO createBonusDTO() {
        BonusDTO bonusDTO = new BonusDTO();
        bonusDTO.setDate("07.12.2021.");
        bonusDTO.setAmount(300);
        bonusDTO.setEmail(EMAIL);
        return bonusDTO;
    }
}