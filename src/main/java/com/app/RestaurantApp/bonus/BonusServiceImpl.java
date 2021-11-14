package com.app.RestaurantApp.bonus;

import com.app.RestaurantApp.salary.Salary;
import com.app.RestaurantApp.salary.SalaryDTO;
import com.app.RestaurantApp.users.employee.Employee;
import com.app.RestaurantApp.users.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class BonusServiceImpl implements BonusService {

    @Autowired
    private BonusRepository bonusRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public List<BonusDTO> getBonusesOfEmployee(String email) {
        Employee e = employeeService.findByEmail(email);
        List<BonusDTO> bonuses = new ArrayList<>();
        for (Bonus b : e.getBonuses())
            bonuses.add(new BonusDTO(b));
        return bonuses;
    }

    @Override
    public BonusDTO createBonus(BonusDTO bonusDTO) {
        bonusDTO.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
        Employee e = employeeService.findByEmail(bonusDTO.getEmail());
        Bonus bonus = new Bonus(bonusDTO);
        bonus.setEmployee(e);
        bonusRepository.save(bonus);
        return bonusDTO;
    }
}
