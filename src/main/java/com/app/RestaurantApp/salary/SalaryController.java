package com.app.RestaurantApp.salary;

import com.app.RestaurantApp.bonus.BonusException;
import com.app.RestaurantApp.salary.dto.SalaryDTO;
import com.app.RestaurantApp.users.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/salaries")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @GetMapping(value = "/getSalariesOfEmployee/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<SalaryDTO>> getSalariesOfEmployee(@PathVariable String email) {
        try {
            return new ResponseEntity<>(salaryService.getSalariesOfEmployee(email), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/createSalary")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> createSalary(@RequestBody SalaryDTO salaryDTO) {
        try {
            salaryService.createSalary(salaryDTO);
        } catch (SalaryException | UserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while adding salary!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Salary added successfully!", HttpStatus.OK);

    }

}
