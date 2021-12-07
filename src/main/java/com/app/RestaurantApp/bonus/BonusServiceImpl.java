package com.app.RestaurantApp.bonus;

import com.app.RestaurantApp.bonus.dto.BonusDTO;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.employee.Employee;
import com.app.RestaurantApp.users.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class BonusServiceImpl implements BonusService {

    @Autowired
    private BonusRepository bonusRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public List<BonusDTO> getBonusesOfEmployee(String email) throws UserException {
        Employee e = employeeService.findEmployeeWithBonuses(email);
        if (e == null) throw new UserException("Invalid employee, email not found!");

        List<BonusDTO> bonuses = new ArrayList<>();
        e.getBonuses().stream().sorted(Comparator.comparingLong(Bonus::getDate)).forEach(item -> bonuses.add(new BonusDTO(item)));
        return bonuses;
    }

    @Override
    public Bonus createBonus(BonusDTO bonusDTO) throws UserException, BonusException {
        bonusDTO.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
        Employee e = employeeService.findByEmail(bonusDTO.getEmail());
        if (e == null) throw new UserException("Invalid employee, email not found!");

        Bonus bonus = new Bonus(bonusDTO);
        bonus.setEmployee(e);
        BonusUtils.CheckBonusInfo(bonus);

        return bonusRepository.save(bonus);

    }
}
