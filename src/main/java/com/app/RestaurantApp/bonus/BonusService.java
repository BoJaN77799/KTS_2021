package com.app.RestaurantApp.bonus;

import com.app.RestaurantApp.bonus.dto.BonusDTO;
import com.app.RestaurantApp.users.UserException;

import java.util.List;

public interface BonusService {

    List<BonusDTO> getBonusesOfEmployee(String email) throws UserException;

    BonusDTO createBonus(BonusDTO bonusDTO) throws UserException;
}
