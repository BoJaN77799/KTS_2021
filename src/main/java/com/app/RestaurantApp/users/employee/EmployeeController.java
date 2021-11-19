package com.app.RestaurantApp.users.employee;

import com.app.RestaurantApp.users.appUser.AppUser;
import com.app.RestaurantApp.users.dto.AppUserAdminUserListDTO;
import com.app.RestaurantApp.users.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping(value = "/search_employees", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER')")
    public List<EmployeeDTO> searchEmployeesManager(
            @RequestParam(value = "searchField", required = false) String searchField,
            @RequestParam(value = "userType", required = false) String userType,
            Pageable pageable) {
        List<Employee> users = employeeService.searchEmployeesManager(searchField, userType, pageable);
        return users.stream().map(EmployeeDTO::new).toList();
    }
}
