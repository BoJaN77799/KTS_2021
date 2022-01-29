package com.app.RestaurantApp.salary;

import com.app.RestaurantApp.salary.dto.SalaryDTO;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.employee.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static com.app.RestaurantApp.salary.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalaryServiceIntegrationTests {

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testGetSalariesOfEmployee() throws UserException {
        List<SalaryDTO> salaries = salaryService.getSalariesOfEmployee(EMAIL_WITH_SALARIES);
        assertEquals(3, salaries.size());

        salaries = salaryService.getSalariesOfEmployee(EMAIL_WITHOUT_SALARIES);
        assertEquals(0, salaries.size());

        Exception exception = assertThrows(UserException.class,
                () -> salaryService.getSalariesOfEmployee("unknown@maildrop.cc"));
        assertEquals(INVALID_USER_MESSAGE, exception.getMessage());
    }

    @Test
    @Transactional
    public void testCreateSalary() throws SalaryException, UserException {
        int salariesSize = salaryRepository.findAll().size();
        assertEquals(3, salariesSize);

        SalaryDTO salaryDTO = new SalaryDTO();
        salaryDTO.setEmail(EMAIL_WITH_SALARIES);
        salaryDTO.setAmount(100);
        salaryDTO.setDateFrom("23.04.2020");

        salaryService.createSalary(salaryDTO);

        assertEquals(salariesSize+1, salaryRepository.findAll().size());
        assertEquals(4, employeeService.findEmployeeWithSalaries(EMAIL_WITH_SALARIES).getSalaries().size());

        salaryDTO.setEmail("unknown@maildrop.cc");
        Exception exception = assertThrows(UserException.class,
                () -> salaryService.createSalary(salaryDTO));
        assertEquals(INVALID_USER_MESSAGE, exception.getMessage());

        salaryDTO.setEmail(EMAIL_WITH_SALARIES);
        salaryDTO.setAmount(-100);
        exception = assertThrows(SalaryException.class,
                () -> salaryService.createSalary(salaryDTO));
        assertEquals(INVALID_AMOUNT, exception.getMessage());
    }
}
