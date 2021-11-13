package com.app.RestaurantApp.bonus;

import java.util.List;

public interface BonusService {

    List<BonusDTO> getBonusesOfEmployee(String email);

    BonusDTO createBonus(BonusDTO bonusDTO);
}
