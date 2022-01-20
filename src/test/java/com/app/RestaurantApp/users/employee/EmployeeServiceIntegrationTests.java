package com.app.RestaurantApp.users.employee;

import com.app.RestaurantApp.enums.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static com.app.RestaurantApp.users.employee.Constants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceIntegrationTests {

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testFindAll() {
        List<Employee> employees = employeeService.findAll();
        assertEquals(4, employees.size());
    }

    @Test
    public void testFindByEmail() {
        Employee e = employeeService.findByEmail(VALID_EMAIL);
        assertNotNull(e);
        assertEquals(VALID_EMAIL, e.getEmail());

        e = employeeService.findByEmail(INVALID_EMAIL);
        assertNull(e);
    }

    @Test
    public void testFindById() {
        Employee e = employeeService.findById(3L);
        assertNotNull(e);

        e = employeeService.findById(11L);
        assertNull(e);
    }

    @Test
    public void testSearchEmployeesManager() {
        List<Employee> employees = employeeService.searchEmployeesManager(SEARCH_VAL_1, EMPTY,
                PageRequest.of(0,2).withSort(Sort.by("firstName").descending()));
        assertEquals(employees.size(), 2);
        assertEquals(3, employees.get(0).getId());
        assertEquals(5, employees.get(1).getId());
        assertTrue(employees.get(0).getFirstName().toLowerCase().contains(SEARCH_VAL_1) &&
                employees.get(1).getFirstName().toLowerCase().contains(SEARCH_VAL_1));

        employees = employeeService.searchEmployeesManager(SEARCH_VAL_2, COOK, null);
        assertEquals(4, employees.get(0).getId());
        assertTrue(employees.get(0).getFirstName().toLowerCase().contains(SEARCH_VAL_2));

        employees = employeeService.searchEmployeesManager(EMPTY, COOK, null);
        assertEquals(2, employees.size());
        assertTrue(employees.get(0).getUserType() == UserType.COOK &&
                employees.get(1).getUserType() == UserType.COOK);

        employees = employeeService.searchEmployeesManager(EMPTY, EMPTY,
                PageRequest.of(0, 10).withSort(Sort.by("firstName").ascending()));
        assertEquals(4, employees.size());
    }

    @Test
    public void testFindEmployeeWithBonuses() {
        Employee e = employeeService.findEmployeeWithBonuses(VALID_EMAIL);
        assertNotNull(e);
        assertEquals(4, e.getBonuses().size());
        assertEquals(4, e.getId());

        e = employeeService.findEmployeeWithBonuses(INVALID_EMAIL);
        assertNull(e);

        e = employeeService.findEmployeeWithBonuses(EMPTY);
        assertNull(e);

        e = employeeService.findEmployeeWithBonuses(VALID_EMAIL_WITHOUT_BONUSES);
        assertNotNull(e);
        assertEquals(0, e.getBonuses().size());
    }

    @Test
    public void testFindEmployeeWithSalaries() {
        Employee e = employeeService.findEmployeeWithSalaries(VALID_EMAIL);
        assertNotNull(e);
        assertEquals(3, e.getSalaries().size());
        assertEquals(4, e.getId());

        e = employeeService.findEmployeeWithSalaries(INVALID_EMAIL);
        assertNull(e);

        e = employeeService.findEmployeeWithSalaries(INVALID_EMAIL);
        assertNull(e);

        e = employeeService.findEmployeeWithSalaries(VALID_EMAIL_WITHOUT_SALARIES);
        assertNotNull(e);
        assertEquals(0, e.getSalaries().size());
    }

    @Test
    public void testFindAllEmployeesWithSalariesAndBonuses() {
        List<Employee> employees = employeeService.findAllEmployeesWithSalariesAndBonuses(false);
        assertEquals(4, employees.size());

        employees = employeeService.findAllEmployeesWithSalariesAndBonuses(true);
        assertEquals(0, employees.size());
    }

    @Test
    public void testFindAllEmployees() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("id"));
        Page<Employee> page = employeeService.findAllEmployees(pageable);
        List<Employee> employees = page.get().toList();

        assertEquals(2, employees.size());
        assertEquals(3L, employees.get(0).getId());
        assertEquals(4L, employees.get(1).getId());

        pageable = PageRequest.of(1, 2, Sort.by("id"));
        page = employeeService.findAllEmployees(pageable);
        employees = page.get().toList();

        assertEquals(2, employees.size());
        assertEquals(5L, employees.get(0).getId());
        assertEquals(6L, employees.get(1).getId());
    }
}
