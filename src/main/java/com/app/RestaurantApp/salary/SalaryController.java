package com.app.RestaurantApp.salary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/salaries")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @GetMapping(value = "/getSalariesOfEmployee/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    private List<SalaryDTO> getSalariesOfEmployee(@PathVariable String email){
        return salaryService.getSalariesOfEmployee(email);
    }

}
