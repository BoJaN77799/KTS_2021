package com.app.RestaurantApp.users.employee;

import com.app.RestaurantApp.users.appUser.AppUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.awt.*;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeeServiceUnitTests {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepositoryMock;

    @Test
    public void testSearchEmployeesManager() {
        doReturn(new PageImpl<Employee>(new ArrayList<>()))
                .when(employeeRepositoryMock).searchEmployeesManager("", "", null);

        employeeService.searchEmployeesManager(null, null, null);
        employeeService.searchEmployeesManager(null, "all", null);

        verify(employeeRepositoryMock, times(2)).searchEmployeesManager("", "", null);
    }
}
