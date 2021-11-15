package com.app.RestaurantApp.bonus;

import com.app.RestaurantApp.bonus.dto.BonusDTO;

import java.util.List;

public interface BonusService {

    List<BonusDTO> getBonusesOfEmployee(String email);

    BonusDTO createBonus(BonusDTO bonusDTO);
}
