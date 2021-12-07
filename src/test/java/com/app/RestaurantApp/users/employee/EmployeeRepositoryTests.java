package com.app.RestaurantApp.users.employee;

import com.app.RestaurantApp.enums.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static com.app.RestaurantApp.users.employee.Constants.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testFindEmployeeWithBonuses() {
        Employee e = employeeRepository.findEmployeeWithBonuses(VALID_EMAIL);
        assertNotNull(e);
        assertEquals(4, e.getBonuses().size());
        assertEquals(4, e.getId());

        e = employeeRepository.findEmployeeWithBonuses(INVALID_EMAIL);
        assertNull(e);

        e = employeeRepository.findEmployeeWithBonuses(EMPTY);
        assertNull(e);

        e = employeeRepository.findEmployeeWithBonuses(VALID_EMAIL_WITHOUT_BONUSES);
        assertNotNull(e);
        assertEquals(0, e.getBonuses().size());
    }

    @Test
    public void testFindEmployeeWithSalaries() {
        Employee e = employeeRepository.findEmployeeWithSalaries(VALID_EMAIL);
        assertNotNull(e);
        assertEquals(3, e.getSalaries().size());
        assertEquals(4, e.getId());

        e = employeeRepository.findEmployeeWithSalaries(INVALID_EMAIL);
        assertNull(e);

        e = employeeRepository.findEmployeeWithSalaries(INVALID_EMAIL);
        assertNull(e);

        e = employeeRepository.findEmployeeWithSalaries(VALID_EMAIL_WITHOUT_SALARIES);
        assertNotNull(e);
        assertEquals(0, e.getSalaries().size());
    }

    @Test
    public void testFindAllEmployeesWithSalariesAndBonuses() {
        List<Employee> employees = employeeRepository.findAllEmployeesWithSalariesAndBonuses(false);
        assertEquals(4, employees.size());

        employees = employeeRepository.findAllEmployeesWithSalariesAndBonuses(true);
        assertEquals(0, employees.size());
    }

    @Test
    public void testSearchEmployeesManager() {
        List<Employee> employees;
        employees = employeeRepository.searchEmployeesManager(SEARCH_VAL_1, EMPTY,
                PageRequest.of(0,2).withSort(Sort.by("firstName").descending()));
        assertEquals(employees.size(), 2);
        assertEquals(3, employees.get(0).getId());
        assertEquals(5, employees.get(1).getId());
        assertTrue(employees.get(0).getFirstName().toLowerCase().contains(SEARCH_VAL_1) &&
                employees.get(1).getFirstName().toLowerCase().contains(SEARCH_VAL_1));

        employees = employeeRepository.searchEmployeesManager(SEARCH_VAL_2, COOK, null);
        assertEquals(4, employees.get(0).getId());
        assertTrue(employees.get(0).getFirstName().toLowerCase().contains(SEARCH_VAL_2));

        employees = employeeRepository.searchEmployeesManager(EMPTY, COOK, null);
        assertEquals(2, employees.size());
        assertTrue(employees.get(0).getUserType() == UserType.COOK &&
                employees.get(1).getUserType() == UserType.COOK);

        employees = employeeRepository.searchEmployeesManager(EMPTY, EMPTY,
                PageRequest.of(0, 10).withSort(Sort.by("firstName").ascending()));
        assertEquals(4, employees.size());
    }
}
