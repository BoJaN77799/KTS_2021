package com.app.RestaurantApp.salary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/salaries")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @GetMapping(value = "/getSalariesOfEmployee/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SalaryDTO> getSalariesOfEmployee(@PathVariable String email){
        return salaryService.getSalariesOfEmployee(email);
    }

    @PostMapping(value = "/createSalary")
    public ResponseEntity<SalaryDTO> createSalary(@RequestBody SalaryDTO salaryDTO){
        return new ResponseEntity<>(salaryService.createSalary(salaryDTO), HttpStatus.OK);
    }

}
