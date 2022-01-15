package com.app.RestaurantApp.salary;

import com.app.RestaurantApp.bonus.BonusException;
import com.app.RestaurantApp.salary.dto.SalaryDTO;
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

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static com.app.RestaurantApp.salary.Constants.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SalaryServiceUnitTests {

    @Autowired
    private SalaryService salaryService;

    @MockBean
    private EmployeeService employeeServiceMock;

    @MockBean
    private SalaryRepository salaryRepositoryMock;

    @BeforeEach
    public void setup() {
        Employee e = createEmployeeWithSalaries();

        given(employeeServiceMock.findByEmail(EMAIL)).willReturn(e);
        given(employeeServiceMock.findEmployeeWithSalaries(EMAIL)).willReturn(e);
    }

    @Test
    public void testGetSalariesOfEmployee_Valid() throws UserException {
        List<SalaryDTO> salaries = salaryService.getSalariesOfEmployee(EMAIL);

        verify(employeeServiceMock, times(1)).findEmployeeWithSalaries(EMAIL);

        assertEquals(3, salaries.size());

        assertEquals(300, salaries.get(0).getAmount());
        assertEquals(200, salaries.get(1).getAmount());
        assertEquals(100, salaries.get(2).getAmount());

        assertEquals("05.10.2021.", salaries.get(0).getDateFrom());
        assertEquals("05.11.2021.", salaries.get(1).getDateFrom());
        assertEquals("05.12.2021.", salaries.get(2).getDateFrom());
    }

    @Test
    public void testGetSalariesOfEmployee_Invalid() {
        given(employeeServiceMock.findEmployeeWithSalaries(any())).willReturn(null);

        Exception exception = assertThrows(UserException.class, () -> salaryService.getSalariesOfEmployee(EMAIL));

        assertEquals(INVALID_USER_MESSAGE, exception.getMessage());
    }

    @Test
    public void testCreateSalary_Valid() throws SalaryException, UserException {
        SalaryDTO salaryDTO = createSalaryDTO();
        Salary salaryMocked = new Salary(salaryDTO);
        Employee e = employeeServiceMock.findEmployeeWithSalaries(salaryDTO.getEmail());
        salaryMocked.setEmployee(e);
        given(salaryRepositoryMock.save(any())).willReturn(salaryMocked);

        salaryService.createSalary(salaryDTO);

        verify(employeeServiceMock, times(2)).findEmployeeWithSalaries(EMAIL);
        verify(salaryRepositoryMock, times(1)).save(any());

    }

    @Test
    public void testCreateSalary_InvalidUser() {
        given(employeeServiceMock.findEmployeeWithSalaries(EMAIL)).willReturn(null);

        Exception exception = assertThrows(UserException.class, () ->
        {
            SalaryDTO salaryDTO = createSalaryDTO();
            salaryService.createSalary(salaryDTO);
        });

        assertEquals(INVALID_USER_MESSAGE, exception.getMessage());
    }

    @Test
    public void testCreateSalary_InvalidAmount() {
        Exception exception = assertThrows(SalaryException.class, () -> {
           SalaryDTO salaryDTO = createSalaryDTO();
           salaryDTO.setAmount(-1);
           salaryService.createSalary(salaryDTO);
        });

        assertEquals(INVALID_AMOUNT, exception.getMessage());
    }

    private Employee createEmployeeWithSalaries() {
        Employee e = new Employee();
        e.setEmail(EMAIL);
        e.setSalaries(new HashSet<>());

        Salary s1 = new Salary(100, 1638658800000L, e);
        Salary s2 = new Salary(200, 1636066800000L, e);
        Salary s3 = new Salary(300, 1633384800000L, e);

        e.getSalaries().add(s1);
        e.getSalaries().add(s2);
        e.getSalaries().add(s3);

        return e;
    }

    private SalaryDTO createSalaryDTO() {
        SalaryDTO salaryDTO = new SalaryDTO();
        salaryDTO.setDateFrom("06.11.2021.");
        salaryDTO.setDateTo("07.11.2021.");
        salaryDTO.setAmount(300);
        salaryDTO.setEmail(Constants.EMAIL);
        return salaryDTO;
    }

}
