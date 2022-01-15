package com.app.RestaurantApp.users.employee;

import com.app.RestaurantApp.ControllerUtils;
import com.app.RestaurantApp.users.dto.EmployeeDTO;
import com.app.RestaurantApp.users.dto.NewEmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<NewEmployeeDTO>> findAll(Pageable pageable) {
        Page<Employee> employees = employeeService.findAllEmployees(pageable);
        return new ResponseEntity<>(employees.stream().map(NewEmployeeDTO::new).toList(),
                ControllerUtils.createPageHeaderAttributes(employees), HttpStatus.OK);
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
