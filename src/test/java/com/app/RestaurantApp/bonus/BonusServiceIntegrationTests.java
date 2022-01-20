package com.app.RestaurantApp.bonus;

import com.app.RestaurantApp.bonus.dto.BonusDTO;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.employee.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static com.app.RestaurantApp.bonus.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BonusServiceIntegrationTests {

    @Autowired
    private BonusService bonusService;

    @Autowired
    private BonusRepository bonusRepository;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testGetBonusesOfEmployee() throws UserException {
        List<BonusDTO> bonuses = bonusService.getBonusesOfEmployee(EMAIL_WITH_BONUSES);
        assertEquals(4, bonuses.size());

        bonuses = bonusService.getBonusesOfEmployee(EMAIL_WITHOUT_BONUSES);
        assertEquals(0, bonuses.size());

        Exception exception = assertThrows(UserException.class,
                () -> bonusService.getBonusesOfEmployee("unknown@maildrop.cc"));
        assertEquals(INVALID_USER_MESSAGE,exception.getMessage());
    }

    @Test
    @Transactional
    public void testCreateBonus() throws BonusException, UserException {
        int bonusesSize = bonusRepository.findAll().size();
        int bonusesOfEmployee = bonusService.getBonusesOfEmployee(EMAIL_WITH_BONUSES).size();
        assertEquals(4, bonusesOfEmployee);
        assertEquals(4, bonusesSize);

        BonusDTO bonusDTO = new BonusDTO();
        bonusDTO.setAmount(100);
        bonusDTO.setEmail(EMAIL_WITH_BONUSES);

        bonusService.createBonus(bonusDTO);
        List<BonusDTO> bonuses = bonusService.getBonusesOfEmployee(EMAIL_WITH_BONUSES);
        assertEquals(5, bonuses.size());

        assertEquals(5, bonusRepository.findAll().size());

        bonusDTO.setEmail("unknown@maildrop.cc");

        Exception exception = assertThrows(UserException.class,
                () -> bonusService.createBonus(bonusDTO));
        assertEquals(INVALID_USER_MESSAGE,exception.getMessage());

        bonusDTO.setAmount(-100);
        bonusDTO.setEmail(EMAIL_WITH_BONUSES);

        exception = assertThrows(BonusException.class,
                () -> bonusService.createBonus(bonusDTO));
        assertEquals(INVALID_AMOUNT,exception.getMessage());
    }
}
